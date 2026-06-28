package com.francesc.neoexplorer.ui.shared.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

/**
 * Canonical window-width breakpoints matching the Material 3 adaptive layout spec:
 * - [Compact] < 600 dp (phones in portrait)
 * - [Medium] 600–839 dp (large phones / tablets in portrait)
 * - [Expanded] ≥ 840 dp (tablets in landscape, desktop)
 */
enum class WindowWidthClass {
  Compact,
  Medium,
  Expanded,
}

/**
 * Returns the current [WindowWidthClass] derived from [LocalWindowInfo], which updates
 * automatically on orientation changes without requiring an Activity reference.
 */
@Composable
fun rememberWindowWidthClass(): WindowWidthClass {
  val containerWidth = LocalWindowInfo.current.containerSize.width
  val widthDp = with(LocalDensity.current) { containerWidth.toDp() }
  return when {
    widthDp < 600.dp -> WindowWidthClass.Compact
    widthDp < 840.dp -> WindowWidthClass.Medium
    else -> WindowWidthClass.Expanded
  }
}
