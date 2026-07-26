package com.francesc.neoexplorer.ui.feature.temporalexplorer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalResources
import com.francesc.neoexplorer.core.formatter.DateFormatter
import com.francesc.neoexplorer.data.neo.NeoRepository
import com.francesc.neoexplorer.ui.feature.details.components.DetailsScreen
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.DateRangeOverlay
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.DateRangeResult
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerScreen
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidUiModel
import com.francesc.neoexplorer.ui.shared.asteroid.NearEarthObjectMapper
import com.francesc.neoexplorer.ui.shared.errormessage.toUserMessage
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.overlay.LocalOverlayHost
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@AssistedInject
class TemporalExplorerPresenter(
  @Assisted private val navigator: Navigator,
  private val neoRepository: NeoRepository,
  private val mapper: NearEarthObjectMapper,
  private val dateFormatter: DateFormatter,
) : Presenter<TemporalExplorerUiState> {

  @CircuitInject(TemporalExplorerScreen::class, AppScope::class)
  @AssistedFactory
  fun interface Factory {
    fun create(@Assisted navigator: Navigator): TemporalExplorerPresenter
  }

  @Composable
  override fun present(): TemporalExplorerUiState {
    val resources = LocalResources.current
    val overlayHost = LocalOverlayHost.current
    val scope = rememberCoroutineScope()

    var loadingState by rememberRetained { mutableStateOf(TemporalExplorerLoadingState.IDLE) }
    var asteroids by rememberRetained { mutableStateOf(emptyList<AsteroidUiModel>()) }
    var hazardousCount by rememberRetained { mutableIntStateOf(0) }
    var startDate by rememberRetained { mutableStateOf<LocalDate?>(null) }
    var endDate by rememberRetained { mutableStateOf<LocalDate?>(null) }
    val displayStartDate =
      remember(key1 = startDate) {
        startDate?.let { dateFormatter.format(it) }
      }
    val displayEndDate =
      remember(key1 = endDate) {
        endDate?.let { dateFormatter.format(it) }
      }
    var errorMessage by rememberRetained { mutableStateOf<String?>(null) }

    suspend fun fetchFeed(start: LocalDate, end: LocalDate) {
      loadingState = TemporalExplorerLoadingState.LOADING
      errorMessage = null
      neoRepository
        .getFeed(startDate = start, endDate = end)
        .fold(
          onSuccess = { feed ->
            val result = mapper.parseFeed(feed)
            asteroids = result.asteroids
            hazardousCount = result.hazardousCount
            loadingState = TemporalExplorerLoadingState.LOADED
          },
          onFailure = { throwable ->
            errorMessage = throwable.toUserMessage(resources)
            loadingState = TemporalExplorerLoadingState.ERROR
          },
        )
    }

    return TemporalExplorerUiState(
      loadingState = loadingState,
      startDate = displayStartDate,
      endDate = displayEndDate,
      asteroids = asteroids,
      hazardousCount = hazardousCount,
      errorMessage = errorMessage,
      eventSink = { event ->
        when (event) {
          TemporalExplorerEvent.ShowDatePicker ->
            scope.launch {
              val result =
                overlayHost.show(
                  DateRangeOverlay(
                    initialStartDate = startDate,
                    initialEndDate = endDate,
                  )
                )
              if (result is DateRangeResult.Selected) {
                startDate = result.startDate
                endDate = result.endDate
                fetchFeed(result.startDate, result.endDate)
              }
            }

          TemporalExplorerEvent.Retry ->
            scope.launch {
              val start = startDate ?: return@launch
              val end = endDate ?: return@launch
              fetchFeed(start, end)
            }

          is TemporalExplorerEvent.AsteroidClicked ->
            navigator.goTo(DetailsScreen(asteroidId = event.asteroidId))
        }
      },
    )
  }
}
