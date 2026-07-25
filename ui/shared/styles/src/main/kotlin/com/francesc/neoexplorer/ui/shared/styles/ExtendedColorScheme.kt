package com.francesc.neoexplorer.ui.shared.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColorScheme(
  val caution: Color,
  val onCaution: Color,
)

val LocalExtendedColorScheme = staticCompositionLocalOf {
  ExtendedColorScheme(
    caution = Color.Unspecified,
    onCaution = Color.Unspecified,
  )
}

val MaterialTheme.extendedColorScheme: ExtendedColorScheme
  @Composable @ReadOnlyComposable get() = LocalExtendedColorScheme.current
