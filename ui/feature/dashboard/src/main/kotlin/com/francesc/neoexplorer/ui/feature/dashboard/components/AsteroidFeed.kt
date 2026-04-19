package com.francesc.neoexplorer.ui.feature.dashboard.components

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
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.plus
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

internal val MinCardSize = 300.dp

@Composable
fun AsteroidFeed(
    asteroids: List<AsteroidUiModel>,
    date: LocalDate,
    hazardousCount: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Crossfade(
        targetState = asteroids.isEmpty(),
        modifier = modifier,
        label = "AsteroidFeedCrossfade"
    ) { isEmpty ->
        if (isEmpty) {
            Column(modifier = Modifier.fillMaxSize()) {
                AsteroidFeedHeader(
                    date = date,
                    hazardousCount = hazardousCount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(contentPadding)
                        .padding(horizontal = MarginDouble, vertical = MarginDouble),
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.no_close_approaches_today),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            val state = rememberLazyGridState()
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = MinCardSize),
                state = state,
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding + PaddingValues(MarginDouble),
                verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
                horizontalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    AsteroidFeedHeader(
                        date = date,
                        hazardousCount = hazardousCount,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                items(asteroids, key = { it.id }) { asteroid ->
                    AsteroidCard(
                        asteroid = asteroid,
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
private fun AsteroidFeedEmptyPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AsteroidFeed(
                emptyList(),
                date = LocalDate(2026, Month.APRIL, 19),
                hazardousCount = 2,
                modifier = Modifier.fillMaxWidth(),
            )
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
                listOf(
                    AsteroidUiModel(
                        id = "2023-CA",
                        name = "(2023 CA)",
                        absoluteMagnitudeH = 22.3,
                        missDistanceLunar = 0.045,
                        missDistanceKm = 2_345_678.0,
                        isPotentiallyHazardous = true,
                        velocityKmPerSecond = 15.1,
                        estimatedDiameterMaxKm = 0.31,
                        closeApproachDate = "19 Apr 2026",
                        threatLevel = ThreatLevel.CAUTION,
                    ),
                    AsteroidUiModel(
                        id = "2023-ZZ",
                        name = "(2023 ZZ)",
                        absoluteMagnitudeH = 16.7,
                        missDistanceLunar = 22.5,
                        missDistanceKm = 8_650_000.0,
                        isPotentiallyHazardous = false,
                        velocityKmPerSecond = 8.2,
                        estimatedDiameterMaxKm = 0.09,
                        closeApproachDate = "19 Apr 2026",
                        threatLevel = ThreatLevel.SAFE,
                    ),
                ),
                date = LocalDate(2026, Month.APRIL, 19),
                hazardousCount = 2,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
