package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@NonRestartableComposable
@Composable
internal fun AsteroidFeedHeader(
  date: String,
  hazardousCount: Int,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Text(
      text = date,
      style = MaterialTheme.typography.titleLarge,
    )
    Text(
      text = stringResource(R.string.potentially_hazardous_objects_today, hazardousCount),
      style = MaterialTheme.typography.bodySmall,
      color =
        if (hazardousCount > 0) {
          MaterialTheme.colorScheme.error
        } else {
          MaterialTheme.colorScheme.onSurfaceVariant
        },
    )
  }
}

@WidgetPreviews
@Composable
private fun AsteroidFeedHeaderHazardousPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      AsteroidFeedHeader(
        date = "25 Jun 2026",
        hazardousCount = 3,
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
      )
    }
  }
}

@WidgetPreviews
@Composable
private fun AsteroidFeedHeaderSafePreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      AsteroidFeedHeader(
        date = "25 Jun 2026",
        hazardousCount = 0,
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
      )
    }
  }
}
