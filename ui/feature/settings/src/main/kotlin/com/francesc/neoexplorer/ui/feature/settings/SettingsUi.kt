package com.francesc.neoexplorer.ui.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.settings.components.SettingsScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope

@CircuitInject(SettingsScreen::class, AppScope::class)
@Composable
fun SettingsUi(
    state: SettingsUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}
