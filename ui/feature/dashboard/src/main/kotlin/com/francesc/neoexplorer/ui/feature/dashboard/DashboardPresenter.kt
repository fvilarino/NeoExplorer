package com.francesc.neoexplorer.ui.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.francesc.neoexplorer.core.clock.DateProvider
import com.francesc.neoexplorer.core.formatter.DateFormatter
import com.francesc.neoexplorer.data.neo.NeoRepository
import com.francesc.neoexplorer.data.neo.model.LunarDistances
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.data.neo.model.NeoFeed
import com.francesc.neoexplorer.ui.feature.dashboard.components.AsteroidUiModel
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.francesc.neoexplorer.ui.feature.dashboard.components.ThreatLevel
import com.francesc.neoexplorer.ui.feature.details.components.DetailsScreen
import com.francesc.neoexplorer.ui.shared.navigation.NavigationBroadcaster
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

@CircuitInject(DashboardScreen::class, AppScope::class)
@Inject
class DashboardPresenter(
  private val neoRepository: NeoRepository,
  private val dateProvider: DateProvider,
  private val dateFormatter: DateFormatter,
  private val navigationBroadcaster: NavigationBroadcaster,
) : Presenter<DashboardUiState> {

  @Composable
  override fun present(): DashboardUiState {
    val today = remember { dateProvider.today() }

    var loadingState by rememberRetained { mutableStateOf(LoadingState.LOADING) }
    var hazardousCount by rememberRetained { mutableIntStateOf(0) }
    var rawAsteroids by rememberRetained { mutableStateOf(emptyList<AsteroidUiModel>()) }
    var sortOrder by rememberRetained { mutableStateOf(SortOrder.BY_DATE) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var retrySignal by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = retrySignal) {
      if (rawAsteroids.isEmpty()) loadingState = LoadingState.LOADING
      errorMessage = null
      neoRepository
        .getFeed(startDate = today, endDate = today.plus(6, DateTimeUnit.DAY))
        .fold(
          onSuccess = { feed ->
            val data = parseFeed(feed)
            rawAsteroids = data.asteroids
            hazardousCount = data.hazardousCount
            loadingState = LoadingState.LOADED
          },
          onFailure = { throwable ->
            errorMessage = throwable.message ?: "Unknown error"
            loadingState = LoadingState.ERROR
          },
        )
    }

    val sortedAsteroids =
      rememberRetained(rawAsteroids, sortOrder) {
        when (sortOrder) {
          SortOrder.BY_DATE -> rawAsteroids
          SortOrder.BY_DISTANCE -> rawAsteroids.sortedBy { it.missDistanceLunar }
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
            navigationBroadcaster.broadcast(DetailsScreen(asteroidId = event.asteroidId))
        }
      },
    )
  }

  private fun parseFeed(feed: NeoFeed): AsteroidFeed {
    val neos =
      feed.nearEarthObjects.entries
        .sortedBy { it.key }
        .flatMap { (date, neos) -> neos.map { neo -> neo.toUiModel(date) } }
    return AsteroidFeed(neos, hazardousCount = neos.count { it.isPotentiallyHazardous })
  }

  private fun NearEarthObject.toUiModel(feedDate: LocalDate): AsteroidUiModel {
    val missDistance =
      closeApproachData.find { it.closeApproachDate == feedDate } ?: closeApproachData.firstOrNull()
    val lunarDist = missDistance?.missDistanceLunar ?: LunarDistances(0.0)
    return AsteroidUiModel(
      id = id.value,
      name = name,
      absoluteMagnitudeH = absoluteMagnitudeH,
      missDistanceLunar = lunarDist.value,
      missDistanceKm = missDistance?.missDistanceKm?.value ?: 0.0,
      isPotentiallyHazardous = isPotentiallyHazardousAsteroid,
      velocityKmPerSecond = missDistance?.relativeVelocityKmPerSecond?.value ?: 0.0,
      estimatedDiameterMaxKm = estimatedDiameter.maxKm.value,
      closeApproachDate =
        missDistance
          ?.closeApproachDate
          ?.let {
            dateFormatter.format(it)
          }
          .orEmpty(),
      threatLevel = ThreatLevel.from(lunarDist.value),
    )
  }

  private data class AsteroidFeed(
    val asteroids: List<AsteroidUiModel>,
    val hazardousCount: Int,
  )
}
