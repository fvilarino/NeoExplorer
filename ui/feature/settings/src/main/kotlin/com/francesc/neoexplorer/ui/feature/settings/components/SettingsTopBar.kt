package com.francesc.neoexplorer.ui.feature.settings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.settings.R
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
fun SettingsTopBar(modifier: Modifier = Modifier) {
  TopAppBar(
    modifier = modifier,
    title = {
      Text(
        text = stringResource(R.string.settings),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth(),
      )
    },
  )
}

@WidgetPreviews
@Composable
private fun SettingsTopBarByDatePreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      SettingsTopBar(modifier = Modifier.fillMaxWidth())
    }
  }
}
