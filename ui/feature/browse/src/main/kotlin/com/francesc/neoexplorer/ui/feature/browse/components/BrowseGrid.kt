package com.francesc.neoexplorer.ui.feature.browse.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidCard
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidFeedErrorContent
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidUiModel
import com.francesc.neoexplorer.ui.shared.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.asteroid.MinCardSize
import com.francesc.neoexplorer.ui.shared.asteroid.ShimmerAsteroidCard
import com.francesc.neoexplorer.ui.shared.asteroid.ThreatLevel
import com.francesc.neoexplorer.ui.shared.asteroid.Velocity
import com.francesc.neoexplorer.ui.shared.compose.LandscapePhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.plus
import com.francesc.neoexplorer.ui.shared.compose.rememberGridContentPadding
import com.francesc.neoexplorer.ui.shared.compose.rememberGridSpacing
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import kotlinx.coroutines.flow.flowOf

internal val LoadingFooterTestTag = "loading_footer"

@Composable
internal fun BrowseGrid(
  asteroids: LazyPagingItems<AsteroidUiModel>,
  onAsteroidClick: (AsteroidId) -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
) {
  val gridState = rememberLazyGridState()
  val gridPadding = rememberGridContentPadding()
  val gridSpacing = rememberGridSpacing()

  LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = MinCardSize),
    state = gridState,
    modifier = modifier,
    contentPadding = contentPadding + PaddingValues(gridPadding),
    verticalArrangement = Arrangement.spacedBy(gridSpacing),
    horizontalArrangement = Arrangement.spacedBy(gridSpacing),
  ) {
    items(
      count = asteroids.itemCount,
      key = asteroids.itemKey { it.id },
    ) { index ->
      val asteroid = asteroids[index]
      if (asteroid != null) {
        AsteroidCard(
          asteroid = asteroid,
          onClick = { onAsteroidClick(asteroid.id) },
          modifier = Modifier.fillMaxWidth(),
        )
      } else {
        // Placeholder while the page loads
        ShimmerAsteroidCard(modifier = Modifier.fillMaxWidth())
      }
    }

    // Append-state footer
    when (val append = asteroids.loadState.append) {
      is LoadState.Loading ->
        item(span = { GridItemSpan(maxLineSpan) }) {
          Box(
            modifier =
              Modifier.fillMaxWidth()
                .padding(vertical = MarginDouble)
                .testTag(LoadingFooterTestTag),
            contentAlignment = Alignment.Center,
          ) {
            CircularProgressIndicator()
          }
        }
      is LoadState.Error ->
        item(span = { GridItemSpan(maxLineSpan) }) {
          AsteroidFeedErrorContent(
            message =
              append.error.message
                ?: stringResource(
                  com.francesc.neoexplorer.ui.shared.compose.R.string.something_went_wrong
                ),
            onRetry = { asteroids.retry() },
            modifier = Modifier.fillMaxWidth().padding(vertical = MarginDouble),
          )
        }
      is LoadState.NotLoading -> Unit
    }
  }
}

// ── Preview helpers ──────────────────────────────────────────────────────────

private val previewAsteroids =
  listOf(
    AsteroidUiModel(
      id = AsteroidId("3542519"),
      name = "(2010 PK9)",
      absoluteMagnitudeH = 21.6,
      missDistance = Distance.km(883_456.0),
      isPotentiallyHazardous = true,
      velocity = Velocity(22.1),
      estimatedDiameterMaxKm = 0.51,
      closeApproachDate = "28 Jun 2026",
      threatLevel = ThreatLevel.DANGER,
    ),
    AsteroidUiModel(
      id = AsteroidId("2465633"),
      name = "465633 (2009 JR5)",
      absoluteMagnitudeH = 19.3,
      missDistance = Distance.km(3_345_678.0),
      isPotentiallyHazardous = false,
      velocity = Velocity(14.3),
      estimatedDiameterMaxKm = 1.02,
      closeApproachDate = "29 Jun 2026",
      threatLevel = ThreatLevel.CAUTION,
    ),
    AsteroidUiModel(
      id = AsteroidId("2137924"),
      name = "137924 (2000 BD19)",
      absoluteMagnitudeH = 17.8,
      missDistance = Distance.km(16_120_000.0),
      isPotentiallyHazardous = false,
      velocity = Velocity(9.8),
      estimatedDiameterMaxKm = 1.88,
      closeApproachDate = "30 Jun 2026",
      threatLevel = ThreatLevel.SAFE,
    ),
    AsteroidUiModel(
      id = AsteroidId("3702915"),
      name = "(2015 DP155)",
      absoluteMagnitudeH = 24.1,
      missDistance = Distance.km(4_378_900.0),
      isPotentiallyHazardous = false,
      velocity = Velocity(6.2),
      estimatedDiameterMaxKm = 0.18,
      closeApproachDate = "1 Jul 2026",
      threatLevel = ThreatLevel.CAUTION,
    ),
  )

@PhonePreviews
@LandscapePhonePreviews
@TabletPreviews
@Composable
private fun BrowseGridPreview() {
  val pagingItems = flowOf(PagingData.from(previewAsteroids)).collectAsLazyPagingItems()
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      BrowseGrid(
        asteroids = pagingItems,
        onAsteroidClick = {},
        modifier = Modifier.fillMaxSize(),
      )
    }
  }
}

@PhonePreviews
@Composable
private fun BrowseGridEmptyPreview() {
  val pagingItems = flowOf(PagingData.empty<AsteroidUiModel>()).collectAsLazyPagingItems()
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      BrowseGrid(
        asteroids = pagingItems,
        onAsteroidClick = {},
        modifier = Modifier.fillMaxSize(),
      )
    }
  }
}
