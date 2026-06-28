package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidFeed as SharedAsteroidFeed
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Velocity
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

internal val MinCardSize = com.francesc.neoexplorer.ui.shared.compose.asteroid.MinCardSize

@Composable
fun AsteroidFeed(
  asteroids: List<AsteroidUiModel>,
  date: LocalDate,
  hazardousCount: Int,
  onAsteroidClick: (AsteroidId) -> Unit,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
) {
  SharedAsteroidFeed(
    asteroids = asteroids,
    onAsteroidClick = onAsteroidClick,
    header = {
      AsteroidFeedHeader(
        date = date,
        hazardousCount = hazardousCount,
        modifier = Modifier.fillMaxWidth(),
      )
    },
    modifier = modifier,
    contentPadding = contentPadding,
    emptyStateMessage = stringResource(R.string.no_close_approaches_today),
  )
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
        date = LocalDate(2026, Month.APRIL, 19),
        hazardousCount = 1,
        onAsteroidClick = {},
        modifier = Modifier.fillMaxWidth(),
      )
    }
  }
}
