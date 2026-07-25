package com.francesc.neoexplorer.ui.feature.browse

import androidx.compose.runtime.Composable
import androidx.paging.cachedIn
import androidx.paging.map
import com.francesc.neoexplorer.core.dispather.DispatcherProvider
import com.francesc.neoexplorer.data.neo.NeoRepository
import com.francesc.neoexplorer.ui.feature.browse.components.BrowseScreen
import com.francesc.neoexplorer.ui.feature.browse.components.collectAsRetainedLazyPagingItems
import com.francesc.neoexplorer.ui.feature.details.components.DetailsScreen
import com.francesc.neoexplorer.ui.shared.asteroid.NearEarthObjectMapper
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map

@AssistedInject
class BrowsePresenter(
  @Assisted private val navigator: Navigator,
  private val neoRepository: NeoRepository,
  private val mapper: NearEarthObjectMapper,
  private val dispatcherProvider: DispatcherProvider,
) : Presenter<BrowseUiState> {

  @CircuitInject(BrowseScreen::class, AppScope::class)
  @AssistedFactory
  fun interface Factory {
    fun create(@Assisted navigator: Navigator): BrowsePresenter
  }

  @Composable
  override fun present(): BrowseUiState {
    // A CoroutineScope retained for the lifetime of this back-stack entry.
    // CancellableRetainedScope implements RememberObserver so rememberRetained will
    // call onForgotten() — and therefore cancel the scope — when the back-stack entry
    // is popped and the retained registry is cleared, preventing a coroutine/memory leak.
    // cachedIn() keeps loaded pages alive in memory so returning from Details
    // never triggers a network reload.
    val retainedScope =
      rememberRetained("browse_pager_scope") {
          CancellableRetainedScope(SupervisorJob() + dispatcherProvider.main)
        }
        .scope

    // Build the mapped+cached paging flow once; retained across composition disposal.
    val pagingFlow =
      rememberRetained("browse_paging_flow") {
        neoRepository
          .browse()
          .map { pagingData -> pagingData.map { neo -> mapper.toBrowseUiModel(neo) } }
          .cachedIn(retainedScope)
      }

    val asteroids = pagingFlow.collectAsRetainedLazyPagingItems()

    return BrowseUiState(
      asteroids = asteroids,
      eventSink = { event ->
        when (event) {
          is BrowseEvent.AsteroidClicked ->
            navigator.goTo(DetailsScreen(asteroidId = event.asteroidId))
        }
      },
    )
  }
}
