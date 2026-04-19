package com.francesc.neoexplorer.ui.feature.dashboard

import androidx.compose.runtime.Stable
import com.francesc.neoexplorer.ui.feature.dashboard.components.AsteroidUiModel
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardEvent
import com.francesc.neoexplorer.ui.feature.dashboard.components.SortOrder
import com.slack.circuit.runtime.CircuitUiState
import kotlinx.datetime.LocalDate

enum class LoadingState { LOADING, LOADED, ERROR }

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
