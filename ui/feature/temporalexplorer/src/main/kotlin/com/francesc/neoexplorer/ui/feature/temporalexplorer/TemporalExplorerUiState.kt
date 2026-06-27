package com.francesc.neoexplorer.ui.feature.temporalexplorer

import androidx.compose.runtime.Stable
import com.slack.circuit.runtime.CircuitUiState
import kotlinx.datetime.LocalDate

enum class TemporalExplorerLoadingState { IDLE, LOADING, LOADED, ERROR }

sealed interface TemporalExplorerEvent {
    data class SetStartDate(val date: LocalDate) : TemporalExplorerEvent
    data class SetEndDate(val date: LocalDate) : TemporalExplorerEvent
    data object Search : TemporalExplorerEvent
}

@Stable
data class TemporalExplorerUiState(
    val loadingState: TemporalExplorerLoadingState = TemporalExplorerLoadingState.IDLE,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val errorMessage: String? = null,
    val eventSink: (TemporalExplorerEvent) -> Unit = {},
) : CircuitUiState
