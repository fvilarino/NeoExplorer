package com.francesc.neoexplorer.ui.feature.settings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
internal fun SwitchPreference(
  title: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  summary: String? = null,
) {
  Preference(
    title = title,
    summary =
      summary?.let {
        {
          Text(
            text = it,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      },
    control = {
      Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
      )
    },
    modifier = modifier,
  )
}

@WidgetPreviews
@Composable
private fun SwitchPreferenceCheckedPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      SwitchPreference(
        title = "Enable feature",
        checked = true,
        onCheckedChange = {},
        modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
      )
    }
  }
}

@WidgetPreviews
@Composable
private fun SwitchPreferenceUncheckedPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      SwitchPreference(
        title = "Enable feature",
        checked = false,
        onCheckedChange = {},
        modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
      )
    }
  }
}

@WidgetPreviews
@Composable
private fun SwitchPreferenceWithSummaryPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      SwitchPreference(
        title = "Enable feature",
        summary = "This is a summary description",
        checked = true,
        onCheckedChange = {},
        modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
      )
    }
  }
}
