package com.francesc.neoexplorer.ui.shared.compose.asteroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.plus
import com.francesc.neoexplorer.ui.shared.compose.rememberGridContentPadding
import com.francesc.neoexplorer.ui.shared.compose.rememberGridSpacing
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
fun AsteroidFeedLoadingContent(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
) {
  LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = MinCardSize),
    modifier = modifier,
    contentPadding = contentPadding + PaddingValues(rememberGridContentPadding()),
    verticalArrangement = Arrangement.spacedBy(rememberGridSpacing()),
    horizontalArrangement = Arrangement.spacedBy(rememberGridSpacing()),
  ) {
    item(span = { GridItemSpan(maxLineSpan) }) {
      ShimmerFeedHeader(modifier = Modifier.fillMaxWidth())
    }
    items(6) {
      ShimmerAsteroidCard(modifier = Modifier.fillMaxWidth())
    }
  }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun AsteroidFeedLoadingContentPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      AsteroidFeedLoadingContent()
    }
  }
}
