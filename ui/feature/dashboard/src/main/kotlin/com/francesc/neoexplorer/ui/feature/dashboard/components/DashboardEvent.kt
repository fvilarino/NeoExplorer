package com.francesc.neoexplorer.ui.feature.dashboard.components

enum class SortOrder { BY_DATE, BY_DISTANCE }

sealed interface DashboardEvent {
    data object Retry : DashboardEvent
    data class SetSortOrder(val sortOrder: SortOrder) : DashboardEvent
}