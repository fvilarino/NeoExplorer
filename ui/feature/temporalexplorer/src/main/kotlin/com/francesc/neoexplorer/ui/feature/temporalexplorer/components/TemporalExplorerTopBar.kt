package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.temporalexplorer.R
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
fun TemporalExplorerTopBar(
  isLoaded: Boolean,
  onSelectDateClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  TopAppBar(
    title = {
      Text(stringResource(R.string.temporal_explorer))
    },
    actions = {
      if (isLoaded) {
        IconButton(onClick = onSelectDateClick) {
          Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = stringResource(R.string.temporal_explorer_change_date_range),
          )
        }
      }
    },
    modifier = modifier,
  )
}

@WidgetPreviews
@Composable
private fun TemporalExplorerTopBarByDatePreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      TemporalExplorerTopBar(
        isLoaded = true,
        onSelectDateClick = {},
        modifier = Modifier.fillMaxWidth(),
      )
    }
  }
}
