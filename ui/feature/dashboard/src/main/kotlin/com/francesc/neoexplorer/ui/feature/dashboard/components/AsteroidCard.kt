package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSizeLarge
import com.francesc.neoexplorer.ui.shared.compose.CardElevation
import com.francesc.neoexplorer.ui.shared.compose.IconSizeSmall
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginQuarter
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

private val ThreatBarWidth = 4.dp

private val ThreatLevel.color: Color
    get() = when (this) {
        ThreatLevel.SAFE -> Color(0xFF4CAF50)
        ThreatLevel.CAUTION -> Color(0xFFFFA000)
        ThreatLevel.DANGER -> Color(0xFFF44336)
    }

private fun formatDistanceKm(km: Double): String = when {
    km >= 1_000_000.0 -> "${"%.1f".format(km / 1_000_000.0)} M km"
    else -> "${"%.0f".format(km / 1_000.0)} K km"
}

private fun formatDiameterM(maxKm: Double): String {
    val meters = maxKm * 1_000.0
    return "~${"%.0f".format(meters)} m"
}

@Composable
fun AsteroidCard(
    asteroid: AsteroidUiModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CardCornerSizeLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Threat-level accent bar
            Box(
                modifier = Modifier
                    .width(ThreatBarWidth)
                    .fillMaxHeight()
                    .background(asteroid.threatLevel.color),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = MarginDouble, vertical = MarginOneAndHalf),
            ) {
                // Header: name + hazardous badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = asteroid.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f),
                    )
                    if (asteroid.isPotentiallyHazardous) {
                        Spacer(modifier = Modifier.width(MarginSingle))
                        HazardousBadge()
                    }
                }

                Spacer(modifier = Modifier.height(MarginSingle))

                // Distance stat
                StatItem(
                    icon = Icons.Filled.Explore,
                    contentDescription = stringResource(R.string.accessibility_miss_distance),
                    label = stringResource(
                        R.string.asteroid_miss_distance,
                        "%.1f".format(asteroid.missDistanceLunar),
                        formatDistanceKm(asteroid.missDistanceKm),
                    ),
                )

                Spacer(modifier = Modifier.height(MarginHalf))

                // Velocity + diameter stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    StatItem(
                        icon = Icons.Filled.Speed,
                        contentDescription = stringResource(R.string.accessibility_velocity),
                        label = stringResource(
                            R.string.asteroid_velocity,
                            "%.1f".format(asteroid.velocityKmPerSecond),
                        ),
                    )
                    StatItem(
                        icon = Icons.Filled.TripOrigin,
                        contentDescription = stringResource(R.string.accessibility_diameter),
                        label = formatDiameterM(asteroid.estimatedDiameterMaxKm),
                    )
                }

                if (asteroid.closeApproachDate.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(MarginQuarter))
                    Text(
                        text = asteroid.closeApproachDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    contentDescription: String?,
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(IconSizeSmall),
        )
        Spacer(modifier = Modifier.width(MarginHalf))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@WidgetPreviews
@Composable
private fun AsteroidCardPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(all = MarginDouble)) {
                AsteroidCard(
                    asteroid = AsteroidUiModel(
                        id = "2025-AB",
                        name = "90416 (2025 AB)",
                        absoluteMagnitudeH = 22.5,
                        missDistanceLunar = 3.2,
                        missDistanceKm = 1_230_456.0,
                        isPotentiallyHazardous = true,
                        velocityKmPerSecond = 18.4,
                        estimatedDiameterMaxKm = 0.42,
                        closeApproachDate = "19 Apr 2026",
                        threatLevel = ThreatLevel.DANGER,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(MarginOneAndHalf))
                AsteroidCard(
                    asteroid = AsteroidUiModel(
                        id = "2024-XY",
                        name = "(2024 XY)",
                        absoluteMagnitudeH = 19.3,
                        missDistanceLunar = 9.1,
                        missDistanceKm = 3_510_000.0,
                        isPotentiallyHazardous = false,
                        velocityKmPerSecond = 12.7,
                        estimatedDiameterMaxKm = 0.18,
                        closeApproachDate = "19 Apr 2026",
                        threatLevel = ThreatLevel.CAUTION,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(MarginOneAndHalf))
                AsteroidCard(
                    asteroid = AsteroidUiModel(
                        id = "2023-ZZ",
                        name = "(2023 ZZ)",
                        absoluteMagnitudeH = 16.7,
                        missDistanceLunar = 22.5,
                        missDistanceKm = 8_650_000.0,
                        isPotentiallyHazardous = false,
                        velocityKmPerSecond = 8.2,
                        estimatedDiameterMaxKm = 0.09,
                        closeApproachDate = "19 Apr 2026",
                        threatLevel = ThreatLevel.SAFE,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
