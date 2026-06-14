package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSizeLarge
import com.francesc.neoexplorer.ui.shared.compose.CardElevation
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
fun MetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    subValue: String? = null,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CardCornerSizeLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MarginDouble, vertical = MarginOneAndHalf),
            verticalArrangement = Arrangement.spacedBy(MarginHalf),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = subValue.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@WidgetPreviews
@Composable
private fun MetricCardPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MetricCard(
                label = "Relative Velocity",
                value = "18.4 km/s",
                subValue = "66,240 km/h",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MarginDouble),
            )
        }
    }
}
