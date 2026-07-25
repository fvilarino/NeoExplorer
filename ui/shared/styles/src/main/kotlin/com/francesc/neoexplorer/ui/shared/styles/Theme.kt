package com.francesc.neoexplorer.ui.shared.styles

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
  )

private val LightColorSchemeCaution = Color(0x20FFA000)
private val LightColorSchemeOnCaution = Color(0xFF362200)
private val DarkColorSchemeCaution = Color(0x20FFA000)
private val DarkColorSchemeOnCaution = Color(0xFF442B00)

@Composable
fun NeoExplorerTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  useDynamicTheme: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      useDynamicTheme && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  val extendedColorScheme =
    ExtendedColorScheme(
      caution = if (darkTheme) DarkColorSchemeCaution else LightColorSchemeCaution,
      onCaution = if (darkTheme) DarkColorSchemeOnCaution else LightColorSchemeOnCaution,
    )

  CompositionLocalProvider(LocalExtendedColorScheme provides extendedColorScheme) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content,
    )
  }
}
