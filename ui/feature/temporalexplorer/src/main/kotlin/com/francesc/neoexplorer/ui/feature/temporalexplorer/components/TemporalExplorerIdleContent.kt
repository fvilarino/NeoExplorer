package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.francesc.neoexplorer.ui.feature.temporalexplorer.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle

@Composable
internal fun TemporalExplorerIdleContent(
  onSelectDateClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(MarginDouble),
      modifier = Modifier.padding(horizontal = MarginDouble),
    ) {
      Text(
        text = stringResource(R.string.temporal_explorer_description),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
      )
      Spacer(modifier = Modifier.height(MarginSingle))
      Button(onClick = onSelectDateClick) {
        Icon(
          imageVector = Icons.Filled.DateRange,
          contentDescription = null,
          modifier = Modifier.padding(end = MarginSingle),
        )
        Text(stringResource(R.string.temporal_explorer_select_date_range))
      }
    }
  }
}
