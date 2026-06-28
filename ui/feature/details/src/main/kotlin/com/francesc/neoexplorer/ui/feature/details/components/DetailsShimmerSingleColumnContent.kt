package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSize
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSizeLarge
import com.francesc.neoexplorer.ui.shared.compose.CardElevation
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.ShimmerLine
import com.francesc.neoexplorer.ui.shared.compose.ShimmerTextStyle
import com.francesc.neoexplorer.ui.shared.compose.rememberShimmerBrush
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

// Matches the SizeComparisonCanvas canvas area:
// LabelHeight(18) + BarHeight(24) + BarGap(12) + BarHeight(24) + LabelHeight(18) = 96 dp
private val ShimmerCanvasHeight = 96.dp

// Approximates AsteroidScaleVisualizer icon row height (heightIn min=80dp, anchor=128dp)
private val ShimmerScaleVisualizerIconHeight = 128.dp

// Matches CanvasMaxWidth cap used in DetailsLoadedSingleColumnContent
private val CanvasMaxWidth = 600.dp

// Button height matches Material3 Button default container height
private val ButtonHeight = 40.dp

@Composable
internal fun DetailsShimmerSingleColumnContent(
  horizontalPadding: Dp,
  modifier: Modifier = Modifier,
) {
  val brush = rememberShimmerBrush()
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding =
      PaddingValues(
        horizontal = horizontalPadding,
        vertical = MarginOneAndHalf,
      ),
    verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
  ) {
    // ── Section title ─────────────────────────────────────────────
    item {
      ShimmerLine(widthFraction = 0.5f, style = ShimmerTextStyle.Title, brush = brush)
    }

    // ── Metrics grid (3 rows × 2 cols mirrors MetricsGrid(columns=2)) ──
    item {
      ShimmerMetricsGrid(brush = brush, columns = 2)
    }

    // ── Divider ───────────────────────────────────────────────────
    item {
      Spacer(modifier = Modifier.height(MarginSingle))
      HorizontalDivider()
      Spacer(modifier = Modifier.height(MarginSingle))
    }

    // ── Size comparison (capped width, same as loaded state) ──────
    item {
      Box(modifier = Modifier.widthIn(max = CanvasMaxWidth).fillMaxWidth()) {
        ShimmerSizeComparisonSection(brush = brush)
      }
    }

    // ── JPL link button ───────────────────────────────────────────
    item {
      Spacer(modifier = Modifier.height(MarginSingle))
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
      ) {
        Box(
          modifier =
            Modifier.widthIn(max = JplButtonMaxWidth)
              .fillMaxWidth()
              .height(ButtonHeight)
              .background(brush, RoundedCornerShape(50))
        )
      }
    }
  }
}

// ── Shared shimmer sub-components ─────────────────────────────────────────────

/**
 * A grid of shimmer [MetricCard] placeholders that mirrors [MetricsGrid] structure. Always renders
 * 3 rows with [columns] cells each (= 6 total metric slots).
 */
@Composable
internal fun ShimmerMetricsGrid(brush: Brush, columns: Int, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(MarginSingle),
  ) {
    repeat(3) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MarginSingle),
      ) {
        repeat(columns) {
          ShimmerMetricCard(brush = brush, modifier = Modifier.weight(1f))
        }
      }
    }
  }
}

/**
 * A shimmer placeholder that exactly mirrors [MetricCard]: same card shape, padding, and three
 * text-line slots (label, value, sub-value).
 */
@Composable
internal fun ShimmerMetricCard(brush: Brush, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(CardCornerSizeLarge),
    elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
  ) {
    Column(
      modifier =
        Modifier.fillMaxWidth().padding(horizontal = MarginDouble, vertical = MarginOneAndHalf),
      verticalArrangement = Arrangement.spacedBy(MarginHalf),
    ) {
      // label (labelSmall → Body)
      ShimmerLine(widthFraction = 0.6f, style = ShimmerTextStyle.Body, brush = brush)
      // value (titleMedium → Title)
      ShimmerLine(widthFraction = 0.8f, style = ShimmerTextStyle.Title, brush = brush)
      // sub-value (bodySmall → Body) – always rendered for uniform card height
      ShimmerLine(widthFraction = 0.5f, style = ShimmerTextStyle.Body, brush = brush)
    }
  }
}

/**
 * A shimmer placeholder that mirrors [SizeComparisonCanvas] + [AsteroidScaleVisualizer]: section
 * title, canvas bar area, divider, scale-visualizer title, and icon area.
 */
@Composable
internal fun ShimmerSizeComparisonSection(brush: Brush, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    // "Size comparison" section title (titleSmall → Title)
    ShimmerLine(widthFraction = 0.55f, style = ShimmerTextStyle.Title, brush = brush)
    Spacer(modifier = Modifier.height(MarginSingle))

    // Bar chart canvas area
    Box(
      modifier =
        Modifier.fillMaxWidth()
          .height(ShimmerCanvasHeight)
          .background(brush, RoundedCornerShape(CardCornerSize))
    )

    // Separator between canvas and scale visualizer (matches SizeComparisonCanvas layout)
    Spacer(modifier = Modifier.height(MarginDouble))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(MarginDouble))

    // "Scale visualizer" title (titleSmall → Title)
    ShimmerLine(widthFraction = 0.45f, style = ShimmerTextStyle.Title, brush = brush)
    Spacer(modifier = Modifier.height(MarginSingle))

    // Icon comparison area (approximates the heightIn(min=80dp) icon row + labels)
    Box(
      modifier =
        Modifier.fillMaxWidth()
          .height(ShimmerScaleVisualizerIconHeight)
          .background(brush, RoundedCornerShape(CardCornerSize))
    )
  }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@PhonePreviews
@Composable
private fun DetailsShimmerSingleColumnContentPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailsShimmerSingleColumnContent(
        horizontalPadding = MarginDouble,
        modifier = Modifier.fillMaxWidth(),
      )
    }
  }
}
