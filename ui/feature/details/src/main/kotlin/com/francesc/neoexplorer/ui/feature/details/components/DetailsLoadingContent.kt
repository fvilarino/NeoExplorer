package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
internal fun DetailsLoadingContent(modifier: Modifier = Modifier) {
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
  }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@PhonePreviews
@Composable
private fun DetailsLoadingContentPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailsLoadingContent(modifier = Modifier.fillMaxWidth().padding(all = MarginDouble))
    }
  }
}
