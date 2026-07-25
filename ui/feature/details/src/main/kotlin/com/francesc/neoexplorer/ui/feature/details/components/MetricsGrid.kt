package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.asteroid.Velocity
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

private data class MetricItem(
  val label: String,
  val value: String,
  val subValue: String? = null,
)

@Composable
internal fun MetricsGrid(
  asteroid: DetailsUiModel,
  modifier: Modifier = Modifier,
  columns: Int = 2,
) {
  val items =
    listOf(
      MetricItem(
        label = stringResource(R.string.metric_diameter_min),
        value = formatKm(asteroid.diameterMinKm),
      ),
      MetricItem(
        label = stringResource(R.string.metric_diameter_max),
        value = formatKm(asteroid.diameterMaxKm),
      ),
      MetricItem(
        label = stringResource(R.string.metric_velocity),
        value =
          if (asteroid.velocity.isKnown) "${"%.2f".format(asteroid.velocity.kmPerSecond)} km/s"
          else "–",
        subValue =
          if (asteroid.velocity.isKnown) "${"%.0f".format(asteroid.velocityKmPerHour)} km/h"
          else null,
      ),
      MetricItem(
        label = stringResource(R.string.metric_orbiting_body),
        value = asteroid.orbitingBody,
      ),
      MetricItem(
        label = stringResource(R.string.metric_miss_distance_km),
        value = formatDistanceKm(asteroid.missDistance),
      ),
      MetricItem(
        label = stringResource(R.string.metric_miss_distance_lunar),
        value =
          if (asteroid.missDistance.isKnown)
            "${"%.2f".format(asteroid.missDistance.inLunarDistances)} LD"
          else "–",
      ),
    )

  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(MarginSingle),
  ) {
    items.chunked(columns).forEach { rowItems ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MarginSingle),
      ) {
        rowItems.forEach { item ->
          MetricCard(
            label = item.label,
            value = item.value,
            subValue = item.subValue,
            modifier = Modifier.weight(1f),
          )
        }
        // Fill any empty trailing slots so weights stay balanced
        repeat(columns - rowItems.size) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@WidgetPreviews
@Composable
private fun MetricsGridPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MetricsGrid(
        asteroid = previewAsteroid(),
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
      )
    }
  }
}

@TabletPreviews
@Composable
private fun MetricsGridTabletPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MetricsGrid(
        asteroid = previewAsteroid(),
        columns = 3,
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
      )
    }
  }
}

private fun previewAsteroid() =
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
  )
