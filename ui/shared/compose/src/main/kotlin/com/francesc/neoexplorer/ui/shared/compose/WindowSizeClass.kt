package com.francesc.neoexplorer.ui.shared.compose

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

/**
 * Returns the current [WindowWidthSizeClass] derived from [LocalWindowInfo], which updates
 * automatically on orientation changes without requiring an Activity reference.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun rememberWindowWidthClass(): WindowWidthSizeClass {
  val containerSize = LocalWindowInfo.current.containerSize
  val dpSize =
    with(LocalDensity.current) {
      DpSize(containerSize.width.toDp(), containerSize.height.toDp())
    }
  return WindowSizeClass.calculateFromSize(dpSize).widthSizeClass
}

/**
 * Returns the appropriate grid content padding for the current window width:
 * - [WindowWidthSizeClass.Compact] → [MarginDouble] (16 dp)
 * - [WindowWidthSizeClass.Medium] → [MarginTreble] (24 dp)
 * - [WindowWidthSizeClass.Expanded] → [MarginQuad] (32 dp)
 */
@Composable
fun rememberGridContentPadding(): Dp =
  when (rememberWindowWidthClass()) {
    WindowWidthSizeClass.Compact -> MarginDouble
    WindowWidthSizeClass.Medium -> MarginTreble
    else -> MarginQuad // Expanded
  }

/**
 * Returns the appropriate grid item spacing for the current window width:
 * - [WindowWidthSizeClass.Compact] → [MarginOneAndHalf] (12 dp)
 * - [WindowWidthSizeClass.Medium] → [MarginDouble] (16 dp)
 * - [WindowWidthSizeClass.Expanded] → [MarginTreble] (24 dp)
 */
@Composable
fun rememberGridSpacing(): Dp =
  when (rememberWindowWidthClass()) {
    WindowWidthSizeClass.Compact -> MarginOneAndHalf
    WindowWidthSizeClass.Medium -> MarginDouble
    else -> MarginTreble // Expanded
  }
