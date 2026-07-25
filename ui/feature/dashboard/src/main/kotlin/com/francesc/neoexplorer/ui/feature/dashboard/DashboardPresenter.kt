package com.francesc.neoexplorer.ui.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalResources
import com.francesc.neoexplorer.core.clock.DateProvider
import com.francesc.neoexplorer.data.neo.NeoRepository
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.francesc.neoexplorer.ui.feature.details.components.DetailsScreen
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidUiModel
import com.francesc.neoexplorer.ui.shared.asteroid.NearEarthObjectMapper
import com.francesc.neoexplorer.ui.shared.errormessage.toUserMessage
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

@AssistedInject
class DashboardPresenter(
  @Assisted private val navigator: Navigator,
  private val neoRepository: NeoRepository,
  private val dateProvider: DateProvider,
  private val mapper: NearEarthObjectMapper,
) : Presenter<DashboardUiState> {

  @CircuitInject(DashboardScreen::class, AppScope::class)
  @AssistedFactory
  fun interface Factory {
    fun create(@Assisted navigator: Navigator): DashboardPresenter
  }

  @Composable
  override fun present(): DashboardUiState {
    val resources = LocalResources.current
    val today = remember { dateProvider.today() }

    var loadingState by rememberRetained { mutableStateOf(LoadingState.LOADING) }
    var hazardousCount by rememberRetained { mutableIntStateOf(0) }
    var rawAsteroids by rememberRetained { mutableStateOf(emptyList<AsteroidUiModel>()) }
    var sortOrder by rememberRetained { mutableStateOf(SortOrder.BY_DATE) }
    var errorMessage by rememberRetained { mutableStateOf<String?>(null) }
    var retrySignal by rememberRetained { mutableIntStateOf(0) }

    LaunchedEffect(key1 = retrySignal) {
      // Skip if this is not a user-triggered retry and we already have a terminal state:
      // • data loaded  → no need to re-fetch on tab returns / config changes
      // • error shown  → user must press Retry explicitly; auto-refetch would flash loading→error
      if (retrySignal == 0 && (rawAsteroids.isNotEmpty() || loadingState == LoadingState.ERROR)) {
        return@LaunchedEffect
      }
      loadingState = LoadingState.LOADING
      errorMessage = null
      neoRepository
        .getFeed(startDate = today, endDate = today.plus(6, DateTimeUnit.DAY))
        .fold(
          onSuccess = { feed ->
            val parsed = mapper.parseFeed(feed)
            rawAsteroids = parsed.asteroids
            hazardousCount = parsed.hazardousCount
            loadingState = LoadingState.LOADED
          },
          onFailure = { throwable ->
            errorMessage = throwable.toUserMessage(resources)
            loadingState = LoadingState.ERROR
          },
        )
    }

    val sortedAsteroids =
      rememberRetained(rawAsteroids, sortOrder) {
        when (sortOrder) {
          SortOrder.BY_DATE -> rawAsteroids
          SortOrder.BY_DISTANCE ->
            rawAsteroids.sortedWith(
              compareBy(nullsLast()) { it.missDistance.inLunarDistancesOrNull }
            )
        }
      }

    return DashboardUiState(
      loadingState = loadingState,
      date = today,
      hazardousCount = hazardousCount,
      asteroids = sortedAsteroids,
      sortOrder = sortOrder,
      errorMessage = errorMessage,
      eventSink = { event ->
        when (event) {
          DashboardEvent.Retry -> ++retrySignal
          is DashboardEvent.SetSortOrder -> sortOrder = event.sortOrder
          is DashboardEvent.AsteroidClicked ->
            navigator.goTo(DetailsScreen(asteroidId = event.asteroidId))
        }
      },
    )
  }
}
