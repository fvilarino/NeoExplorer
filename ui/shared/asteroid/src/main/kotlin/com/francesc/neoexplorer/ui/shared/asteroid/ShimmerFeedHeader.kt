package com.francesc.neoexplorer.ui.shared.asteroid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.ShimmerLine
import com.francesc.neoexplorer.ui.shared.compose.ShimmerTextStyle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.compose.rememberShimmerBrush
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
fun ShimmerFeedHeader(modifier: Modifier = Modifier) {
  val brush = rememberShimmerBrush()
  Column(modifier = modifier) {
    ShimmerLine(widthFraction = 0.45f, style = ShimmerTextStyle.Title, brush = brush)
    Spacer(modifier = Modifier.height(MarginSingle))
    ShimmerLine(widthFraction = 0.65f, style = ShimmerTextStyle.Body, brush = brush)
  }
}

@WidgetPreviews
@Composable
private fun ShimmerFeedHeaderPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      ShimmerFeedHeader(modifier = Modifier.fillMaxWidth().padding(MarginDouble))
    }
  }
}
