package com.francesc.neoexplorer.ui.shared.asteroid

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.R
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.plus
import com.francesc.neoexplorer.ui.shared.compose.rememberGridContentPadding
import com.francesc.neoexplorer.ui.shared.compose.rememberGridSpacing
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

val MinCardSize = 300.dp

@Composable
fun AsteroidFeed(
  asteroids: List<AsteroidUiModel>,
  onAsteroidClick: (AsteroidId) -> Unit,
  header: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
  emptyStateMessage: String? = null,
) {
  Crossfade(
    targetState = asteroids.isEmpty(),
    modifier = modifier,
    label = "AsteroidFeedCrossfade",
  ) { isEmpty ->
    if (isEmpty) {
      Column(modifier = Modifier.fillMaxSize()) {
        Box(
          modifier =
            Modifier.fillMaxWidth()
              .padding(contentPadding)
              .padding(horizontal = MarginDouble, vertical = MarginDouble)
        ) {
          header()
        }
        Box(
          modifier = Modifier.weight(1f).fillMaxWidth(),
          contentAlignment = Alignment.Center,
        ) {
          Text(
            text = emptyStateMessage ?: stringResource(R.string.no_asteroids_in_range),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    } else {
      val state = rememberLazyGridState()
      val gridPadding = rememberGridContentPadding()
      val gridSpacing = rememberGridSpacing()

      LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = MinCardSize),
        state = state,
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding + PaddingValues(gridPadding),
        verticalArrangement = Arrangement.spacedBy(gridSpacing),
        horizontalArrangement = Arrangement.spacedBy(gridSpacing),
      ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          Box(modifier = Modifier.fillMaxWidth()) { header() }
        }
        items(asteroids, key = { it.id }) { asteroid ->
          AsteroidCard(
            asteroid = asteroid,
            onClick = { onAsteroidClick(asteroid.id) },
            modifier = Modifier.fillMaxWidth(),
          )
        }
      }
    }
  }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun AsteroidFeedPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      AsteroidFeed(
        asteroids =
          listOf(
            AsteroidUiModel(
              id = AsteroidId("2023-CA"),
              name = "(2023 CA)",
              absoluteMagnitudeH = 22.3,
              missDistance = Distance.km(2_345_678.0),
              isPotentiallyHazardous = true,
              velocity = Velocity(15.1),
              estimatedDiameterMaxKm = 0.31,
              closeApproachDate = "19 Apr 2026",
              threatLevel = ThreatLevel.CAUTION,
            )
          ),
        onAsteroidClick = {},
        header = { Text("Preview Header") },
        modifier = Modifier.fillMaxWidth(),
      )
    }
  }
}
