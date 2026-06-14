package com.francesc.neoexplorer.ui.feature.details.components

sealed interface DetailsEvent {
    data object Retry : DetailsEvent
    data object BackClicked : DetailsEvent
}
