package com.francesc.neoexplorer.ui.feature.settings

import com.francesc.neoexplorer.data.preferences.AppTheme
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

data class SettingsUiState(
  val isLoading: Boolean,
  val theme: AppTheme,
  val useDynamicTheme: Boolean,
  val eventSink: (SettingsUiEvent) -> Unit,
) : CircuitUiState

sealed interface SettingsUiEvent : CircuitUiEvent {
  data class ThemeChanged(val theme: AppTheme) : SettingsUiEvent

  data class DynamicThemeChanged(val useDynamicTheme: Boolean) : SettingsUiEvent
}
