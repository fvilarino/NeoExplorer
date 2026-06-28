package com.francesc.neoexplorer.ui.feature.browse

import androidx.compose.runtime.Composable
import androidx.paging.cachedIn
import androidx.paging.map
import com.francesc.neoexplorer.core.dispather.DispatcherProvider
import com.francesc.neoexplorer.core.formatter.DateFormatter
import com.francesc.neoexplorer.data.neo.NeoRepository
import com.francesc.neoexplorer.data.neo.model.LunarDistances
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.ui.feature.browse.components.BrowseScreen
import com.francesc.neoexplorer.ui.feature.browse.components.collectAsRetainedLazyPagingItems
import com.francesc.neoexplorer.ui.feature.details.components.DetailsScreen
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidUiModel
import com.francesc.neoexplorer.ui.shared.compose.asteroid.ThreatLevel
import com.francesc.neoexplorer.ui.shared.navigation.NavigationBroadcaster
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map

@CircuitInject(BrowseScreen::class, AppScope::class)
@Inject
class BrowsePresenter(
  private val neoRepository: NeoRepository,
  private val dateFormatter: DateFormatter,
  private val navigationBroadcaster: NavigationBroadcaster,
  private val dispatcherProvider: DispatcherProvider,
) : Presenter<BrowseUiState> {

  @Composable
  override fun present(): BrowseUiState {
    // A CoroutineScope retained for the lifetime of this back-stack entry.
    // cachedIn() keeps loaded pages alive in memory so returning from Details
    // never triggers a network reload.
    val retainedScope =
      rememberRetained("browse_pager_scope") {
        CoroutineScope(SupervisorJob() + dispatcherProvider.main)
      }

    // Build the mapped+cached paging flow once; retained across composition disposal.
    val pagingFlow =
      rememberRetained("browse_paging_flow") {
        neoRepository
          .browse()
          .map { pagingData -> pagingData.map { neo -> neo.toUiModel() } }
          .cachedIn(retainedScope)
      }

    val asteroids = pagingFlow.collectAsRetainedLazyPagingItems()

    return BrowseUiState(
      asteroids = asteroids,
      eventSink = { event ->
        when (event) {
          is BrowseEvent.AsteroidClicked ->
            navigationBroadcaster.broadcast(DetailsScreen(asteroidId = event.asteroidId))
        }
      },
    )
  }

  private fun NearEarthObject.toUiModel(): AsteroidUiModel {
    val missDistance = closeApproachData.firstOrNull()
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
        missDistance?.closeApproachDate?.let { dateFormatter.format(it) }.orEmpty(),
      threatLevel = ThreatLevel.from(lunarDist.value),
    )
  }
}
