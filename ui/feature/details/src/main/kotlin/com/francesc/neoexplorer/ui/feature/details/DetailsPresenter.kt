package com.francesc.neoexplorer.ui.feature.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.francesc.neoexplorer.core.formatter.DateFormatter
import com.francesc.neoexplorer.data.neo.NeoRepository
import com.francesc.neoexplorer.data.neo.model.AsteroidId as DataAsteroidId
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.ui.feature.details.components.DetailsScreen
import com.francesc.neoexplorer.ui.feature.details.components.DetailsUiModel
import com.francesc.neoexplorer.ui.feature.details.components.SizeReferenceObject
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Velocity
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

class DetailsPresenter
@AssistedInject
constructor(
  @Assisted private val screen: DetailsScreen,
  @Assisted private val navigator: Navigator,
  private val neoRepository: NeoRepository,
  private val dateFormatter: DateFormatter,
) : Presenter<DetailsUiState> {

  @Composable
  override fun present(): DetailsUiState {
    var loadingState by remember { mutableStateOf(DetailsLoadingState.LOADING) }
    var asteroid by remember { mutableStateOf<DetailsUiModel?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var retrySignal by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = retrySignal) {
      loadingState = DetailsLoadingState.LOADING
      errorMessage = null
      neoRepository
        .lookupAsteroid(DataAsteroidId(screen.asteroidId.value))
        .fold(
          onSuccess = { neo ->
            asteroid = neo.toUiModel()
            loadingState = DetailsLoadingState.LOADED
          },
          onFailure = { throwable ->
            errorMessage = throwable.message ?: "Unknown error"
            loadingState = DetailsLoadingState.ERROR
          },
        )
    }

    return DetailsUiState(
      loadingState = loadingState,
      asteroid = asteroid,
      errorMessage = errorMessage,
      eventSink = { event ->
        when (event) {
          DetailsEvent.Retry -> ++retrySignal
          DetailsEvent.BackClicked -> navigator.pop()
        }
      },
    )
  }

  private fun NearEarthObject.toUiModel(): DetailsUiModel {
    val approach = closeApproachData.firstOrNull()
    val diameterMaxKm = estimatedDiameter.maxKm.value
    return DetailsUiModel(
      id = AsteroidId(id.value),
      name = name,
      isPotentiallyHazardous = isPotentiallyHazardousAsteroid,
      diameterMinKm = estimatedDiameter.minKm.value,
      diameterMaxKm = diameterMaxKm,
      velocity = approach?.relativeVelocityKmPerSecond?.value?.let(::Velocity) ?: Velocity.UNKNOWN,
      missDistance = approach?.missDistanceKm?.value?.let { Distance.km(it) } ?: Distance.UNKNOWN,
      orbitingBody = approach?.orbitingBody ?: "—",
      nasaJplUrl = nasaJplUrl.value,
      closeApproachDate = approach?.closeApproachDate?.let { dateFormatter.format(it) }.orEmpty(),
      sizeReference = SizeReferenceObject.from(diameterMaxKm * 1_000.0),
    )
  }

  @CircuitInject(DetailsScreen::class, AppScope::class)
  @AssistedFactory
  fun interface Factory {
    fun create(screen: DetailsScreen, navigator: Navigator): DetailsPresenter
  }
}
