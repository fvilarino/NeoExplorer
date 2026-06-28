package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.ContentContainer
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginQuad
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.rememberWindowWidthClass
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

/**
 * Loading skeleton for the Details screen.
 *
 * Branches on [WindowWidthSizeClass] to show a shimmer layout that mirrors the real loaded content:
 * - [WindowWidthSizeClass.Expanded] → two-pane shimmer ([DetailsShimmerTwoPaneContent])
 * - [WindowWidthSizeClass.Medium] / [WindowWidthSizeClass.Compact] → single-column shimmer
 *   ([DetailsShimmerSingleColumnContent]) centred inside [ContentContainer]
 */
@Composable
internal fun DetailsLoadingContent(modifier: Modifier = Modifier) {
  val windowWidthClass = rememberWindowWidthClass()

  if (windowWidthClass == WindowWidthSizeClass.Expanded) {
    DetailsShimmerTwoPaneContent(modifier = modifier)
  } else {
    val horizontalPadding =
      if (windowWidthClass == WindowWidthSizeClass.Medium) {
        MarginQuad
      } else {
        MarginDouble
      }
    ContentContainer(modifier = modifier) {
      DetailsShimmerSingleColumnContent(
        horizontalPadding = horizontalPadding,
        modifier = Modifier.fillMaxWidth(),
      )
    }
  }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@PhonePreviews
@Composable
private fun DetailsLoadingContentPhonePreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailsLoadingContent()
    }
  }
}

@TabletPreviews
@Composable
private fun DetailsLoadingContentTabletPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailsLoadingContent()
    }
  }
}
