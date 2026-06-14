package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

private val BarHeight: Dp = 24.dp
private val BarGap: Dp = 12.dp
private val LabelHeight: Dp = 18.dp

/**
 * Draws a two-bar horizontal comparison between the asteroid's diameter and a known reference
 * object.  Both bars are scaled relative to the larger of the two values so the chart always
 * fills the available width.
 */
@Composable
fun SizeComparisonCanvas(
    asteroidName: String,
    asteroidDiameterKm: Double,
    reference: SizeReferenceObject,
    modifier: Modifier = Modifier,
) {
    val asteroidColor = Color(0xFFF44336)
    val referenceColor = Color(0xFF4CAF50)
    val onSurface = MaterialTheme.colorScheme.onSurface
    val textMeasurer = rememberTextMeasurer()
    val asteroidInlineLabel = stringResource(R.string.size_comparison_asteroid_label)
    val sectionTitle = stringResource(R.string.size_comparison_title)

    val asteroidSizeLabel = remember(key1 = asteroidDiameterKm) { formatKm(asteroidDiameterKm) }
    val referenceLabel = remember(key1 = reference) { "${reference.label}  (${formatKm(reference.sizeMeters / 1_000.0)})" }

    val labelStyle = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium)

    Column(modifier = modifier) {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(MarginSingle))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(LabelHeight + BarHeight + BarGap + BarHeight + LabelHeight),
        ) {
            val referenceSizeKm = reference.sizeMeters / 1_000.0
            val maxKm = maxOf(asteroidDiameterKm, referenceSizeKm)
            val availableWidth = size.width

            val asteroidBarWidth =
                ((asteroidDiameterKm / maxKm) * availableWidth).toFloat().coerceAtLeast(4f)
            val referenceBarWidth =
                ((referenceSizeKm / maxKm) * availableWidth).toFloat().coerceAtLeast(4f)

            val barHeightPx = BarHeight.toPx()
            val barGapPx = BarGap.toPx()
            val labelHeightPx = LabelHeight.toPx()
            val cornerRadius = CornerRadius(6f, 6f)

            // ── Asteroid bar ──────────────────────────────────────────────
            val asteroidBarTop = labelHeightPx
            drawRoundRect(
                color = asteroidColor,
                topLeft = Offset(0f, asteroidBarTop),
                size = Size(asteroidBarWidth, barHeightPx),
                cornerRadius = cornerRadius,
            )

            // Size label drawn above the bar
            val asteroidSizeMeasured = textMeasurer.measure(asteroidSizeLabel, labelStyle)
            drawText(
                textLayoutResult = asteroidSizeMeasured,
                color = onSurface,
                topLeft = Offset(0f, 0f),
            )

            // "Asteroid" label drawn inside the bar
            val asteroidInlineMeasured = textMeasurer.measure(asteroidInlineLabel, labelStyle)
            val asteroidValueX =
                (asteroidBarWidth - asteroidInlineMeasured.size.width - 8f).coerceAtLeast(4f)
            drawText(
                textLayoutResult = asteroidInlineMeasured,
                color = Color.White,
                topLeft = Offset(
                    asteroidValueX,
                    asteroidBarTop + (barHeightPx - asteroidInlineMeasured.size.height) / 2f,
                ),
            )

            // ── Reference bar ─────────────────────────────────────────────
            val referenceBarTop = asteroidBarTop + barHeightPx + barGapPx
            drawRoundRect(
                color = referenceColor,
                topLeft = Offset(0f, referenceBarTop),
                size = Size(referenceBarWidth, barHeightPx),
                cornerRadius = cornerRadius,
            )

            // Label drawn below the reference bar
            val refMeasured = textMeasurer.measure(referenceLabel, labelStyle)
            drawText(
                textLayoutResult = refMeasured,
                color = onSurface,
                topLeft = Offset(0f, referenceBarTop + barHeightPx + 2f),
            )
        }

        // ── Illustrative icon comparison ──────────────────────────────────
        Spacer(modifier = Modifier.height(MarginDouble))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(MarginDouble))
        AsteroidScaleVisualizer(
            asteroidName = asteroidName,
            diameterMeters = asteroidDiameterKm * 1_000.0,
            reference = reference,
        )
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@WidgetPreviews
@Composable
private fun SizeComparisonCanvasPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SizeComparisonCanvas(
                asteroidName = "2020 CD3",
                asteroidDiameterKm = 0.42,
                reference = SizeReferenceObject.BURJ_KHALIFA,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MarginDouble),
            )
        }
    }
}
