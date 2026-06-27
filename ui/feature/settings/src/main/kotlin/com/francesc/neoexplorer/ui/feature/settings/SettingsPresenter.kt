package com.francesc.neoexplorer.ui.feature.settings

import androidx.compose.runtime.Composable
import com.francesc.neoexplorer.ui.feature.settings.components.SettingsScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(SettingsScreen::class, AppScope::class)
@Inject
class SettingsPresenter : Presenter<SettingsUiState> {

    @Composable
    override fun present(): SettingsUiState = SettingsUiState
}
