package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSize
import com.francesc.neoexplorer.ui.shared.compose.IconSizeSmall
import com.francesc.neoexplorer.ui.shared.compose.MarginHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
fun HazardousBadge(modifier: Modifier = Modifier) {
    val amberColor = Color(0xFFFFA000)
    Row(
        modifier = modifier
            .background(
                color = amberColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(CardCornerSize)
            )
            .padding(horizontal = MarginSingle, vertical = MarginHalf),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MarginHalf),
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = stringResource(R.string.accessibility_potentially_hazardous),
            tint = amberColor,
            modifier = Modifier.size(IconSizeSmall),
        )
        Text(
            text = stringResource(R.string.potentially_hazardous_abbreviation),
            style = MaterialTheme.typography.labelSmall,
            color = amberColor,
            fontWeight = FontWeight.Bold,
        )
    }
}

@WidgetPreviews
@Composable
private fun HazardousBadgePreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HazardousBadge(
                modifier = Modifier.padding(MarginSingle),
            )
        }
    }
}
