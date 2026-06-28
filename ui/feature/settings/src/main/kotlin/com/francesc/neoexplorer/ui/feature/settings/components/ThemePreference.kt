package com.francesc.neoexplorer.ui.feature.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francesc.neoexplorer.data.preferences.AppTheme
import com.francesc.neoexplorer.ui.feature.settings.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
internal fun ThemePreference(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    Preference(
        title = stringResource(R.string.settings_theme_section),
        control = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MarginSingle),
            ) {
                ThemeIcon(
                    icon = Icons.Default.AutoMode,
                    onClick = { onThemeSelected(AppTheme.AUTO) },
                    isSelected = selectedTheme == AppTheme.AUTO,
                    contentDescription = AppTheme.AUTO.label,
                )
                ThemeIcon(
                    icon = Icons.Default.LightMode,
                    onClick = { onThemeSelected(AppTheme.LIGHT) },
                    isSelected = selectedTheme == AppTheme.LIGHT,
                    contentDescription = AppTheme.LIGHT.label,
                )
                ThemeIcon(
                    icon = Icons.Default.DarkMode,
                    onClick = { onThemeSelected(AppTheme.DARK) },
                    isSelected = selectedTheme == AppTheme.DARK,
                    contentDescription = AppTheme.DARK.label,
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun ThemeIcon(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    FilledIconToggleButton(
        checked = isSelected,
        onCheckedChange = { onClick() },
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            tint = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            contentDescription = contentDescription,
        )
    }
}

private val AppTheme.label: String
    @Composable get() = when (this) {
        AppTheme.AUTO -> stringResource(R.string.settings_theme_auto)
        AppTheme.LIGHT -> stringResource(R.string.settings_theme_light)
        AppTheme.DARK -> stringResource(R.string.settings_theme_dark)
    }

private class AppThemePreviewProvider : PreviewParameterProvider<AppTheme> {
    override val values = AppTheme.entries.asSequence()
}

@WidgetPreviews
@Composable
private fun ThemePreferencePreview(
    @PreviewParameter(AppThemePreviewProvider::class) theme: AppTheme,
) {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ThemePreference(
                selectedTheme = theme,
                onThemeSelected = {},
                modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
            )
        }
    }
}
