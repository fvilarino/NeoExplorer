package com.francesc.neoexplorer.ui.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.francesc.neoexplorer.data.preferences.AppPreferencesRepository
import com.francesc.neoexplorer.data.preferences.AppTheme
import com.francesc.neoexplorer.ui.feature.settings.components.SettingsScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@CircuitInject(SettingsScreen::class, AppScope::class)
@Inject
class SettingsPresenter(
    private val appPreferencesRepository: AppPreferencesRepository,
) : Presenter<SettingsUiState> {

    @Composable
    override fun present(): SettingsUiState {
        var isLoading by rememberRetained { mutableStateOf(true) }
        var theme by rememberRetained { mutableStateOf(AppTheme.AUTO) }
        var useDynamicTheme by rememberRetained { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit) {
            appPreferencesRepository.preferences.collectLatest { prefs ->
                theme = prefs.theme
                useDynamicTheme = prefs.useDynamicTheme
                isLoading = false
            }
        }

        return SettingsUiState(
            isLoading = isLoading,
            theme = theme,
            useDynamicTheme = useDynamicTheme,
            eventSink = { event ->
                when (event) {
                    is SettingsUiEvent.ThemeChanged -> scope.launch {
                        appPreferencesRepository.setTheme(event.theme)
                    }
                    is SettingsUiEvent.DynamicThemeChanged -> scope.launch {
                        appPreferencesRepository.setUseDynamicTheme(event.useDynamicTheme)
                    }
                }
            },
        )
    }
}
