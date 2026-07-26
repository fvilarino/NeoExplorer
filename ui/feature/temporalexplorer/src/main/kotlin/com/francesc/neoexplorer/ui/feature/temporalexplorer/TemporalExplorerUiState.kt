package com.francesc.neoexplorer.ui.feature.temporalexplorer

import androidx.compose.runtime.Stable
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidUiModel
import com.slack.circuit.runtime.CircuitUiState

enum class TemporalExplorerLoadingState {
  IDLE,
  LOADING,
  LOADED,
  ERROR,
}

sealed interface TemporalExplorerEvent {
  data object ShowDatePicker : TemporalExplorerEvent

  data object Retry : TemporalExplorerEvent

  data class AsteroidClicked(val asteroidId: AsteroidId) : TemporalExplorerEvent
}

@Stable
data class TemporalExplorerUiState(
  val loadingState: TemporalExplorerLoadingState = TemporalExplorerLoadingState.IDLE,
  val startDate: String? = null,
  val endDate: String? = null,
  val asteroids: List<AsteroidUiModel> = emptyList(),
  val hazardousCount: Int = 0,
  val errorMessage: String? = null,
  val eventSink: (TemporalExplorerEvent) -> Unit = {},
) : CircuitUiState
