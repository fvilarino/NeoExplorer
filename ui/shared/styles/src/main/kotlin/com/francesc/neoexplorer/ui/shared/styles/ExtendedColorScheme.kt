package com.francesc.neoexplorer.ui.shared.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable data class ExtendedColorScheme(val amber: Color)

val LocalExtendedColorScheme = staticCompositionLocalOf {
  ExtendedColorScheme(amber = Color.Unspecified)
}

val MaterialTheme.extendedColorScheme: ExtendedColorScheme
  @Composable @ReadOnlyComposable get() = LocalExtendedColorScheme.current
