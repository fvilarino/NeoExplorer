package com.francesc.neoexplorer.ui.feature.dashboard

import androidx.compose.runtime.Stable
import com.francesc.neoexplorer.ui.feature.dashboard.components.AsteroidUiModel
import com.slack.circuit.runtime.CircuitUiState
import kotlinx.datetime.LocalDate

enum class LoadingState { LOADING, LOADED, ERROR }

enum class SortOrder { BY_DATE, BY_DISTANCE }

sealed interface DashboardEvent {
    data object Retry : DashboardEvent
    data class SetSortOrder(val sortOrder: SortOrder) : DashboardEvent
    data class AsteroidClicked(val asteroidId: String) : DashboardEvent
}

@Stable
data class DashboardUiState(
    val loadingState: LoadingState = LoadingState.LOADING,
    val date: LocalDate,
    val hazardousCount: Int = 0,
    val asteroids: List<AsteroidUiModel> = emptyList(),
    val sortOrder: SortOrder = SortOrder.BY_DATE,
    val errorMessage: String? = null,
    val eventSink: (DashboardEvent) -> Unit = {},
) : CircuitUiState
