package com.francesc.neoexplorer.ui.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francesc.neoexplorer.ui.feature.details.components.DetailsErrorContent
import com.francesc.neoexplorer.ui.feature.details.components.DetailsLoadedSingleColumnContent
import com.francesc.neoexplorer.ui.feature.details.components.DetailsLoadedTwoPaneContent
import com.francesc.neoexplorer.ui.feature.details.components.DetailsLoadingContent
import com.francesc.neoexplorer.ui.feature.details.components.DetailsScreen
import com.francesc.neoexplorer.ui.feature.details.components.DetailsUiModel
import com.francesc.neoexplorer.ui.feature.details.components.SizeReferenceObject
import com.francesc.neoexplorer.ui.shared.compose.ContentContainer
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginQuad
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Velocity
import com.francesc.neoexplorer.ui.shared.compose.plus
import com.francesc.neoexplorer.ui.shared.compose.rememberWindowWidthClass
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope

@CircuitInject(DetailsScreen::class, AppScope::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsUi(
  state: DetailsUiState,
  modifier: Modifier = Modifier,
) {
  val sink = state.eventSink
  DetailsUi(
    state = state,
    onBackClick = { sink(DetailsEvent.BackClicked) },
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsUi(
  state: DetailsUiState,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        navigationIcon = {
          IconButton(onClick = onBackClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Default.ArrowBack,
              contentDescription = stringResource(R.string.accessibility_back),
              tint = MaterialTheme.colorScheme.onSurface,
            )
          }
        },
        title = {
          Column {
            Text(
              text = state.asteroid?.name ?: stringResource(R.string.details_loading_title),
              style = MaterialTheme.typography.titleMedium,
            )
            if (state.loadingState == DetailsLoadingState.LOADED) {
              Text(
                text = state.asteroid?.closeApproachDate.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }
        },
      )
    },
  ) { innerPadding ->
    when (state.loadingState) {
      DetailsLoadingState.LOADING ->
        DetailsLoadingContent(modifier = Modifier.padding(innerPadding).fillMaxSize())

      DetailsLoadingState.ERROR ->
        DetailsErrorContent(
          message = state.errorMessage ?: stringResource(R.string.something_went_wrong),
          onRetry = { state.eventSink(DetailsEvent.Retry) },
          modifier =
            Modifier.padding(innerPadding + PaddingValues(all = MarginDouble)).fillMaxSize(),
        )

      DetailsLoadingState.LOADED -> {
        val asteroid = state.asteroid ?: return@Scaffold
        val windowWidthClass = rememberWindowWidthClass()

        if (windowWidthClass == WindowWidthSizeClass.Expanded) {
          DetailsLoadedTwoPaneContent(
            asteroid = asteroid,
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
          )
        } else {
          // Compact / Medium – single column, capped at MaxContentWidth and centered
          val horizontalPadding =
            if (windowWidthClass == WindowWidthSizeClass.Medium) {
              MarginQuad
            } else {
              MarginDouble
            }
          ContentContainer(modifier = Modifier.fillMaxSize()) {
            DetailsLoadedSingleColumnContent(
              asteroid = asteroid,
              horizontalPadding = horizontalPadding,
              modifier = Modifier.fillMaxWidth(),
              contentPadding = innerPadding,
            )
          }
        }
      }
    }
  }
}

// ── Previews ──────────────────────────────────────────────────────────────────

private class DetailsUiStateProvider : PreviewParameterProvider<DetailsUiState> {
  override val values =
    sequenceOf(
      // Loaded
      DetailsUiState(
        loadingState = DetailsLoadingState.LOADED,
        asteroid =
          DetailsUiModel(
            id = AsteroidId("2025-AB"),
            name = "90416 (2025 AB)",
            isPotentiallyHazardous = true,
            diameterMinKm = 0.18,
            diameterMaxKm = 0.42,
            velocity = Velocity(18.4),
            missDistance = Distance.km(1_230_456.0),
            orbitingBody = "Earth",
            nasaJplUrl = "https://ssd.jpl.nasa.gov/tools/sbdb_lookup.html#/?sstr=2025-AB",
            closeApproachDate = "19 Apr 2026",
            sizeReference = SizeReferenceObject.BURJ_KHALIFA,
          ),
      ),
      // Loading
      DetailsUiState(loadingState = DetailsLoadingState.LOADING),
      // Error
      DetailsUiState(
        loadingState = DetailsLoadingState.ERROR,
        errorMessage = "Failed to load asteroid data. Please try again.",
      ),
    )
}

@PhonePreviews
@Composable
private fun DetailsUiPhonePreview(
  @PreviewParameter(DetailsUiStateProvider::class) state: DetailsUiState
) {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailsUi(state = state, modifier = Modifier.fillMaxSize())
    }
  }
}

@TabletPreviews
@Composable
private fun DetailsUiTabletPreview(
  @PreviewParameter(DetailsUiStateProvider::class) state: DetailsUiState
) {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailsUi(state = state, modifier = Modifier.fillMaxSize())
    }
  }
}
