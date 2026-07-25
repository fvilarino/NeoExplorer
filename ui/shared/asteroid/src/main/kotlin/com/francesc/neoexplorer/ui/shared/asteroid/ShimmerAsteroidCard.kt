package com.francesc.neoexplorer.ui.shared.asteroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSizeLarge
import com.francesc.neoexplorer.ui.shared.compose.CardElevation
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.ShimmerLine
import com.francesc.neoexplorer.ui.shared.compose.ShimmerSpacing
import com.francesc.neoexplorer.ui.shared.compose.ShimmerTextStyle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.compose.rememberShimmerBrush
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

private val ShimmerBarWidth = 4.dp

@Composable
fun ShimmerAsteroidCard(modifier: Modifier = Modifier) {
  val brush = rememberShimmerBrush()
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(CardCornerSizeLarge),
    elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
  ) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
      Box(modifier = Modifier.width(ShimmerBarWidth).fillMaxHeight().background(brush))
      Column(modifier = Modifier.padding(horizontal = MarginDouble, vertical = MarginDouble)) {
        ShimmerLine(widthFraction = 0.6f, style = ShimmerTextStyle.Title, brush = brush)
        Spacer(modifier = Modifier.height(ShimmerSpacing))
        ShimmerLine(widthFraction = 0.7f, style = ShimmerTextStyle.Body, brush = brush)
        Spacer(modifier = Modifier.height(MarginSingle))
        Row(modifier = Modifier.fillMaxWidth()) {
          ShimmerLine(
            widthFraction = 1f,
            style = ShimmerTextStyle.Body,
            modifier = Modifier.weight(1f),
            brush = brush,
          )
          Spacer(modifier = Modifier.width(MarginDouble))
          ShimmerLine(
            widthFraction = 1f,
            style = ShimmerTextStyle.Body,
            modifier = Modifier.weight(1f),
            brush = brush,
          )
        }
        Spacer(modifier = Modifier.height(MarginSingle))
        ShimmerLine(widthFraction = 0.25f, style = ShimmerTextStyle.Body, brush = brush)
      }
    }
  }
}

@WidgetPreviews
@Composable
private fun ShimmerAsteroidCardPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      ShimmerAsteroidCard(modifier = Modifier.padding(all = MarginDouble))
    }
  }
}
