package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSize
import com.francesc.neoexplorer.ui.shared.compose.IconSizeSmall
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import com.francesc.neoexplorer.ui.shared.styles.extendedColorScheme

@Composable
internal fun HazardousWarningBanner(modifier: Modifier = Modifier) {
  val amberColor = MaterialTheme.extendedColorScheme.amber
  Row(
    modifier =
      modifier
        .background(
          color = amberColor.copy(alpha = 0.12f),
          shape = RoundedCornerShape(CardCornerSize),
        )
        .padding(horizontal = MarginDouble, vertical = MarginOneAndHalf),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = Icons.Default.Warning,
      contentDescription = null,
      tint = amberColor,
      modifier = Modifier.size(IconSizeSmall),
    )
    Spacer(modifier = Modifier.width(MarginSingle))
    Text(
      text = stringResource(R.string.potentially_hazardous_description),
      style = MaterialTheme.typography.bodySmall,
      color = amberColor,
    )
  }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@PhonePreviews
@TabletPreviews
@Composable
private fun HazardousWarningBannerPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      HazardousWarningBanner(modifier = Modifier.fillMaxWidth().padding(all = MarginDouble))
    }
  }
}
