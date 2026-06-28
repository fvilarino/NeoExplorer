package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.ShimmerLine
import com.francesc.neoexplorer.ui.shared.compose.ShimmerTextStyle
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.rememberShimmerBrush
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

private val ButtonHeight = 40.dp

@Composable
internal fun DetailsShimmerTwoPaneContent(modifier: Modifier = Modifier) {
  val brush = rememberShimmerBrush()
  Row(
    modifier = modifier.padding(horizontal = MarginDouble),
    horizontalArrangement = Arrangement.spacedBy(MarginDouble),
  ) {
    // ── Left pane: section title + 3-column metrics grid ──────────
    LazyColumn(
      modifier = Modifier.weight(1f),
      contentPadding = PaddingValues(vertical = MarginOneAndHalf),
      verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
    ) {
      item {
        ShimmerLine(widthFraction = 0.5f, style = ShimmerTextStyle.Title, brush = brush)
      }
      item {
        ShimmerMetricsGrid(brush = brush, columns = 3, modifier = Modifier.fillMaxWidth())
      }
    }

    VerticalDivider(modifier = Modifier.fillMaxHeight())

    // ── Right pane: size comparison + JPL button ──────────────────
    LazyColumn(
      modifier = Modifier.weight(1f),
      contentPadding = PaddingValues(vertical = MarginOneAndHalf),
      verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
    ) {
      item {
        ShimmerSizeComparisonSection(
          brush = brush,
          modifier = Modifier.fillMaxWidth(),
        )
      }
      item {
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
}

// ── Preview ───────────────────────────────────────────────────────────────────

@TabletPreviews
@Composable
private fun DetailsShimmerTwoPaneContentPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailsShimmerTwoPaneContent(modifier = Modifier.fillMaxWidth())
    }
  }
}
