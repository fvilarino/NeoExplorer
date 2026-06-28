package com.francesc.neoexplorer.ui.feature.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
internal fun Preference(
  title: String,
  modifier: Modifier = Modifier,
  summary: (@Composable () -> Unit)? = null,
  control: (@Composable () -> Unit)? = null,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
      )
      summary?.invoke()
    }
    control?.let {
      Column(modifier = Modifier.padding(start = MarginDouble)) {
        it()
      }
    }
  }
}

@WidgetPreviews
@Composable
private fun PreferenceTitleOnlyPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      Preference(
        title = "Preference title",
        modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
      )
    }
  }
}

@WidgetPreviews
@Composable
private fun PreferenceWithSummaryPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      Preference(
        title = "Preference title",
        summary = {
          Text(
            text = "This is a summary description",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        },
        modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
      )
    }
  }
}

@WidgetPreviews
@Composable
private fun PreferenceWithControlPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      Preference(
        title = "Preference title",
        summary = {
          Text(
            text = "This is a summary description",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        },
        control = { Switch(checked = true, onCheckedChange = {}) },
        modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
      )
    }
  }
}
