package com.francesc.neoexplorer.ui.feature.temporalexplorer

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerFeedHeader
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerIdleContent
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerScreen
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerTopBar
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidFeed
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidFeedErrorContent
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidFeedLoadingContent
import com.francesc.neoexplorer.ui.shared.compose.LocalHomeScaffoldPadding
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.plus
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope

@CircuitInject(TemporalExplorerScreen::class, AppScope::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemporalExplorerUi(
  state: TemporalExplorerUiState,
  modifier: Modifier = Modifier,
) {
  val homePadding = LocalHomeScaffoldPadding.current
  Scaffold(
    modifier = modifier,
    topBar = {
      TemporalExplorerTopBar(
        isLoaded = state.loadingState == TemporalExplorerLoadingState.LOADED,
        onSelectDateClick = { state.eventSink(TemporalExplorerEvent.ShowDatePicker) },
        modifier = Modifier.fillMaxWidth(),
      )
    },
  ) { innerPadding ->
    val contentPadding = innerPadding + homePadding
    Crossfade(
      targetState = state.loadingState,
      label = "temporalExplorerState",
    ) { loadingState ->
      when (loadingState) {
        TemporalExplorerLoadingState.IDLE ->
          TemporalExplorerIdleContent(
            onSelectDateClick = { state.eventSink(TemporalExplorerEvent.ShowDatePicker) },
            modifier = Modifier.fillMaxSize().padding(contentPadding),
          )

        TemporalExplorerLoadingState.LOADING ->
          AsteroidFeedLoadingContent(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
          )

        TemporalExplorerLoadingState.LOADED ->
          AsteroidFeed(
            asteroids = state.asteroids,
            onAsteroidClick = { state.eventSink(TemporalExplorerEvent.AsteroidClicked(it)) },
            header = {
              val start = state.startDate
              val end = state.endDate
              if (start != null && end != null) {
                TemporalExplorerFeedHeader(
                  startDate = start,
                  endDate = end,
                  hazardousCount = state.hazardousCount,
                  modifier = Modifier.fillMaxWidth(),
                )
              }
            },
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
          )

        TemporalExplorerLoadingState.ERROR ->
          AsteroidFeedErrorContent(
            message =
              state.errorMessage
                ?: stringResource(
                  com.francesc.neoexplorer.ui.shared.compose.R.string.something_went_wrong
                ),
            onRetry = { state.eventSink(TemporalExplorerEvent.Retry) },
            modifier =
              Modifier.padding(contentPadding + PaddingValues(all = MarginDouble)).fillMaxSize(),
          )
      }
    }
  }
}
