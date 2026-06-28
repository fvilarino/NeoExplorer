package com.francesc.neoexplorer.ui.feature.browse

import androidx.compose.runtime.Stable
import androidx.paging.compose.LazyPagingItems
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidUiModel
import com.slack.circuit.runtime.CircuitUiState

sealed interface BrowseEvent {
  data class AsteroidClicked(val asteroidId: String) : BrowseEvent
}

/**
 * UI state for the Browse screen. Uses a regular class (not data class) because [LazyPagingItems]
 * does not have meaningful structural equality.
 */
@Stable
class BrowseUiState(
  val asteroids: LazyPagingItems<AsteroidUiModel>,
  val eventSink: (BrowseEvent) -> Unit,
) : CircuitUiState
