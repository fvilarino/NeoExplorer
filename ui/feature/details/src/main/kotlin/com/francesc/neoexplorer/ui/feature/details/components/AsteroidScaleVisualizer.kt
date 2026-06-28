package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

private val AnchorSizeDp: Dp = 128.dp
private val MinIconSizeDp: Dp = 16.dp

private data class IllustrativeSize(
  val referenceObject: Dp,
  val asteroid: Dp,
)

/**
 * Two-segment linear scale:
 *
 * • Asteroid ≤ reference → reference icon stays at [AnchorSizeDp]; asteroid icon =
 * (asteroidDiameter / referenceSize) × AnchorSizeDp.
 *
 * • Asteroid > reference → asteroid icon stays at [AnchorSizeDp]; reference icon = (referenceSize /
 * asteroidDiameter) × AnchorSizeDp.
 *
 * Both results are clamped to [MinIconSizeDp] so neither icon vanishes entirely.
 *
 * @return IllustrativeSize(referenceIconSizeDp, asteroidIconSizeDp)
 */
private fun calculateLinearScale(
  asteroidDiameterMeters: Double,
  referenceSizeMeters: Double,
): IllustrativeSize =
  if (asteroidDiameterMeters <= referenceSizeMeters) {
    val asteroidDp =
      (AnchorSizeDp * (asteroidDiameterMeters / referenceSizeMeters).toFloat()).coerceAtLeast(
        MinIconSizeDp
      )
    IllustrativeSize(AnchorSizeDp, asteroidDp)
  } else {
    val referenceDp =
      (AnchorSizeDp * (referenceSizeMeters / asteroidDiameterMeters).toFloat()).coerceAtLeast(
        MinIconSizeDp
      )
    IllustrativeSize(referenceDp, AnchorSizeDp)
  }

@Composable
internal fun AsteroidScaleVisualizer(
  asteroidName: String,
  diameterMeters: Double,
  reference: SizeReferenceObject,
  modifier: Modifier = Modifier,
) {
  val (referenceIconSizeDp, asteroidIconSizeDp) =
    calculateLinearScale(
      asteroidDiameterMeters = diameterMeters,
      referenceSizeMeters = reference.sizeMeters,
    )

  Column(modifier = modifier) {
    Text(
      text = stringResource(R.string.scale_visualizer_title),
      style = MaterialTheme.typography.titleSmall,
      color = MaterialTheme.colorScheme.onSurface,
    )

    Spacer(modifier = Modifier.size(MarginSingle))

    Row(
      modifier = Modifier.fillMaxWidth().wrapContentHeight().heightIn(min = 80.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.Bottom, // shared ground baseline
    ) {
      // ── Reference object (anchor) ─────────────────────────────────
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
          painter = painterResource(id = reference.iconRes),
          contentDescription =
            stringResource(
              R.string.scale_visualizer_anchor_cd,
              reference.label,
            ),
          modifier = Modifier.size(referenceIconSizeDp),
        )
        Text(
          text = reference.label,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      Spacer(modifier = Modifier.width(MarginDouble))

      // ── Asteroid silhouette ───────────────────────────────────────
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
          painter = painterResource(id = R.drawable.asteroid),
          contentDescription = stringResource(R.string.scale_visualizer_asteroid_cd),
          modifier = Modifier.size(asteroidIconSizeDp),
        )
        Text(
          text =
            stringResource(
              R.string.scale_visualizer_asteroid_label,
              asteroidName,
              diameterMeters.toInt(),
            ),
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
  }
}

// ── Previews ──────────────────────────────────────────────────────────────────

/** Tiny asteroid (~1.5 m) vs Bicycle (~1.7 m) */
@WidgetPreviews
@Composable
private fun AsteroidScaleVisualizerTinyPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      AsteroidScaleVisualizer(
        asteroidName = "2013 NF19",
        diameterMeters = 1.5,
        reference = SizeReferenceObject.BICYCLE,
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
      )
    }
  }
}

/** Mid-range asteroid (~500 m) vs Golden Gate Bridge (~2 737 m) */
@WidgetPreviews
@Composable
private fun AsteroidScaleVisualizerMidPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      AsteroidScaleVisualizer(
        asteroidName = "2013 NF19",
        diameterMeters = 500.0,
        reference = SizeReferenceObject.GOLDEN_GATE_BRIDGE,
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
      )
    }
  }
}

/** Very large asteroid (~11 000 m) vs Mount Everest (~8 849 m) */
@WidgetPreviews
@Composable
private fun AsteroidScaleVisualizerLargePreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      AsteroidScaleVisualizer(
        asteroidName = "2013 NF19",
        diameterMeters = 11_000.0,
        reference = SizeReferenceObject.MOUNT_EVEREST,
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
      )
    }
  }
}
