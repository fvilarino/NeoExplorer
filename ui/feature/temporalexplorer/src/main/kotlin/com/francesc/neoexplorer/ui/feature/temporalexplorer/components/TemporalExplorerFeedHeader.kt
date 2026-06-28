package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.temporalexplorer.R
import kotlinx.datetime.LocalDate

@NonRestartableComposable
@Composable
internal fun TemporalExplorerFeedHeader(
  startDate: LocalDate,
  endDate: LocalDate,
  hazardousCount: Int,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Text(
      text =
        stringResource(
          R.string.temporal_explorer_date_range_header,
          formatDate(startDate),
          formatDate(endDate),
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

internal fun formatDate(date: LocalDate): String {
  val monthName = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
  return "${date.day} $monthName ${date.year}"
}
