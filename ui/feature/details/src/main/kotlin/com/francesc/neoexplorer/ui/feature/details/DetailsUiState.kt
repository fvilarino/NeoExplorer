package com.francesc.neoexplorer.ui.feature.details

import androidx.compose.runtime.Stable
import com.francesc.neoexplorer.ui.feature.details.components.DetailsUiModel
import com.slack.circuit.runtime.CircuitUiState

enum class DetailsLoadingState {
  LOADING,
  LOADED,
  ERROR,
}

sealed interface DetailsEvent {
  data object Retry : DetailsEvent

  data object BackClicked : DetailsEvent
}

@Stable
data class DetailsUiState(
  val loadingState: DetailsLoadingState = DetailsLoadingState.LOADING,
  val asteroid: DetailsUiModel? = null,
  val errorMessage: String? = null,
  val eventSink: (DetailsEvent) -> Unit = {},
) : CircuitUiState
