package com.francesc.neoexplorer.ui.feature.dashboard.components

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
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.plus
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = MinCardSize),
        modifier = modifier,
        contentPadding = contentPadding + PaddingValues(MarginDouble),
        verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
        horizontalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ShimmerHeader(modifier = Modifier.fillMaxWidth())
        }
        items(6) {
            ShimmerCard(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun LoadingContentPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LoadingContent()
        }
    }
}
