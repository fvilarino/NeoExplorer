package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSize
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSizeLarge
import com.francesc.neoexplorer.ui.shared.compose.CardElevation
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.ShimmerHeightBody
import com.francesc.neoexplorer.ui.shared.compose.ShimmerHeightTitle
import com.francesc.neoexplorer.ui.shared.compose.ShimmerSpacing
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

private val ShimmerBarWidth = 4.dp

@Composable
fun ShimmerCard(modifier: Modifier = Modifier) {
    val shimmerBrush = rememberShimmerBrush()
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CardCornerSizeLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = CardElevation),
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // Accent bar placeholder
            Box(
                modifier = Modifier
                    .width(ShimmerBarWidth)
                    .fillMaxHeight()
                    .background(shimmerBrush),
            )
            Column(modifier = Modifier.padding(horizontal = MarginDouble, vertical = MarginDouble)) {
                // Title row
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(ShimmerHeightTitle)
                        .background(shimmerBrush, RoundedCornerShape(CardCornerSize)),
                )
                Spacer(modifier = Modifier.height(ShimmerSpacing))
                // Distance stat
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(ShimmerHeightBody)
                        .background(shimmerBrush, RoundedCornerShape(CardCornerSize)),
                )
                Spacer(modifier = Modifier.height(MarginSingle))
                // Velocity + diameter row
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(ShimmerHeightBody)
                            .background(shimmerBrush, RoundedCornerShape(CardCornerSize)),
                    )
                    Spacer(modifier = Modifier.width(MarginDouble))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(ShimmerHeightBody)
                            .background(shimmerBrush, RoundedCornerShape(CardCornerSize)),
                    )
                }
                Spacer(modifier = Modifier.height(MarginSingle))
                // Date footer
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .height(ShimmerHeightBody)
                        .background(shimmerBrush, RoundedCornerShape(CardCornerSize)),
                )
            }
        }
    }
}

@Composable
internal fun rememberShimmerBrush(): Brush {
    val baseColor = MaterialTheme.colorScheme.surfaceVariant
    val highlightColor = MaterialTheme.colorScheme.surface
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1_200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1_200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerTranslate",
    )
    return Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = Offset(x = translateAnim - 600f, y = 0f),
        end = Offset(x = translateAnim + 600f, y = 0f),
    )
}

@WidgetPreviews
@Composable
private fun ShimmerCardPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ShimmerCard(
                modifier = Modifier.padding(all = MarginDouble),
            )
        }
    }
}
