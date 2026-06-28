package com.francesc.neoexplorer.data.preferences

/**
 * Snapshot of all user-facing app preferences.
 *
 * @property theme The selected colour-scheme / theme.
 * @property useDynamicTheme Whether to use Material You dynamic colour theming.
 */
data class AppPreferences(
    val theme: AppTheme,
    val useDynamicTheme: Boolean,
)
