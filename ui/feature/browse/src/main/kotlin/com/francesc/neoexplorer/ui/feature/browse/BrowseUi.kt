package com.francesc.neoexplorer.ui.feature.browse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import com.francesc.neoexplorer.ui.feature.browse.components.BrowseGrid
import com.francesc.neoexplorer.ui.feature.browse.components.BrowseScreen
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidFeedErrorContent
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidFeedLoadingContent
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope

@CircuitInject(BrowseScreen::class, AppScope::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseUi(state: BrowseUiState, modifier: Modifier = Modifier) {
  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(title = { Text(text = stringResource(R.string.browse_title)) })
    },
  ) { innerPadding ->
    when (val refresh = state.asteroids.loadState.refresh) {
      is LoadState.Loading ->
        AsteroidFeedLoadingContent(
          modifier = Modifier.fillMaxSize(),
          displayHeader = false,
          contentPadding = innerPadding,
        )
      is LoadState.Error ->
        AsteroidFeedErrorContent(
          message =
            refresh.error.message
              ?: stringResource(
                com.francesc.neoexplorer.ui.shared.compose.R.string.something_went_wrong
              ),
          onRetry = { state.asteroids.retry() },
          modifier = Modifier.padding(innerPadding).fillMaxSize(),
        )
      is LoadState.NotLoading ->
        BrowseGrid(
          asteroids = state.asteroids,
          onAsteroidClick = { state.eventSink(BrowseEvent.AsteroidClicked(it)) },
          modifier = Modifier.fillMaxSize(),
          contentPadding = innerPadding,
        )
    }
  }
}
