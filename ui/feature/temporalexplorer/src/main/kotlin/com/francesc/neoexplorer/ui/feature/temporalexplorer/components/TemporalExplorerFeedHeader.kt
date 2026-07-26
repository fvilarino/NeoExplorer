package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.temporalexplorer.R

@NonRestartableComposable
@Composable
internal fun TemporalExplorerFeedHeader(
  startDate: String,
  endDate: String,
  hazardousCount: Int,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Text(
      text =
        stringResource(
          R.string.temporal_explorer_date_range_header,
          startDate,
          endDate,
        ),
      style = MaterialTheme.typography.titleLarge,
    )
    Text(
      text = stringResource(R.string.temporal_explorer_hazardous_objects, hazardousCount),
      style = MaterialTheme.typography.bodySmall,
      color =
        if (hazardousCount > 0) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}
