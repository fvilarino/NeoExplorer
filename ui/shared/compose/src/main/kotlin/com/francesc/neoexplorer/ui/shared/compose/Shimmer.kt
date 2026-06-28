package com.francesc.neoexplorer.ui.shared.compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

/** Defines which text style a shimmer line placeholder should emulate. */
enum class ShimmerTextStyle {
  /** Emulates a title-sized text row (maps to [ShimmerHeightTitle]). */
  Title,

  /** Emulates a body-sized text row (maps to [ShimmerHeightBody]). */
  Body,
}

private fun ShimmerTextStyle.toDp(): Dp =
  when (this) {
    ShimmerTextStyle.Title -> ShimmerHeightTitle
    ShimmerTextStyle.Body -> ShimmerHeightBody
  }

/**
 * Creates and remembers the animated shimmer brush.
 *
 * Exposed publicly so feature modules can share a single brush instance across bespoke shimmer
 * layouts, keeping all placeholder lines animated in perfect sync.
 */
@Composable
fun rememberShimmerBrush(): Brush {
  val baseColor = MaterialTheme.colorScheme.surfaceVariant
  val highlightColor = MaterialTheme.colorScheme.surface
  val transition = rememberInfiniteTransition(label = "shimmer")
  val translateAnim by
    transition.animateFloat(
      initialValue = 0f,
      targetValue = 1_200f,
      animationSpec =
        infiniteRepeatable(
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

/**
 * A single animated shimmer placeholder line.
 *
 * @param widthFraction Fraction of the parent width to fill (0f–1f).
 * @param style Determines the placeholder height to match the target text style.
 * @param modifier Optional additional modifiers (e.g. `Modifier.weight` inside a Row).
 * @param brush Animated shimmer brush. Defaults to a new [rememberShimmerBrush]; pass a shared
 *   brush when multiple lines should animate in sync.
 */
@Composable
fun ShimmerLine(
  widthFraction: Float,
  style: ShimmerTextStyle,
  modifier: Modifier = Modifier,
  brush: Brush = rememberShimmerBrush(),
) {
  Box(
    modifier =
      modifier
        .fillMaxWidth(widthFraction)
        .height(style.toDp())
        .background(brush, RoundedCornerShape(CardCornerSize))
  )
}

/**
 * Two stacked shimmer placeholder lines separated by [ShimmerSpacing].
 *
 * Useful for label+value or title+subtitle pairs.
 *
 * @param brush Animated shimmer brush. Defaults to a new [rememberShimmerBrush]; pass a shared
 *   brush when multiple lines should animate in sync.
 */
@Composable
fun ShimmerTwoLines(
  firstStyle: ShimmerTextStyle,
  secondStyle: ShimmerTextStyle,
  modifier: Modifier = Modifier,
  firstWidthFraction: Float = 0.55f,
  secondWidthFraction: Float = 0.70f,
  brush: Brush = rememberShimmerBrush(),
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(ShimmerSpacing),
  ) {
    ShimmerLine(widthFraction = firstWidthFraction, style = firstStyle, brush = brush)
    ShimmerLine(widthFraction = secondWidthFraction, style = secondStyle, brush = brush)
  }
}

// ── Previews ──────────────────────────────────────────────────────────────────

/**
 * Shows both [ShimmerTextStyle] variants — [ShimmerTextStyle.Title] (taller) on top,
 * [ShimmerTextStyle.Body] (shorter) below — at representative width fractions.
 */
@WidgetPreviews
@Composable
private fun ShimmerLinePreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      val brush = rememberShimmerBrush()
      Column(
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
        verticalArrangement = Arrangement.spacedBy(MarginSingle),
      ) {
        ShimmerLine(widthFraction = 0.6f, style = ShimmerTextStyle.Title, brush = brush)
        ShimmerLine(widthFraction = 0.8f, style = ShimmerTextStyle.Body, brush = brush)
      }
    }
  }
}

/**
 * Demonstrates [ShimmerTwoLines] with default fractions (title + body pair) and a reversed pair
 * (body + title), simulating typical label/value placeholders.
 */
@WidgetPreviews
@Composable
private fun ShimmerTwoLinesPreview() {
  NeoExplorerTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      val brush = rememberShimmerBrush()
      Column(
        modifier = Modifier.fillMaxWidth().padding(MarginDouble),
        verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
      ) {
        // Title + Body (e.g. section heading + subtitle)
        ShimmerTwoLines(
          firstStyle = ShimmerTextStyle.Title,
          secondStyle = ShimmerTextStyle.Body,
          brush = brush,
        )
        // Body + Title (e.g. label + value inside a metric card)
        ShimmerTwoLines(
          firstStyle = ShimmerTextStyle.Body,
          secondStyle = ShimmerTextStyle.Title,
          firstWidthFraction = 0.45f,
          secondWidthFraction = 0.65f,
          brush = brush,
        )
      }
    }
  }
}
