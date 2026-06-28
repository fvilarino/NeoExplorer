package com.francesc.neoexplorer.ui.feature.settings

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.data.preferences.AppTheme
import com.francesc.neoexplorer.ui.feature.settings.components.SettingsScreen
import com.francesc.neoexplorer.ui.feature.settings.components.SettingsTopBar
import com.francesc.neoexplorer.ui.feature.settings.components.SwitchPreference
import com.francesc.neoexplorer.ui.feature.settings.components.ThemePreference
import com.francesc.neoexplorer.ui.shared.compose.ContentContainer
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope

@CircuitInject(SettingsScreen::class, AppScope::class)
@Composable
fun SettingsUi(
  state: SettingsUiState,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    topBar = {
      SettingsTopBar(modifier = Modifier.fillMaxWidth())
    },
    modifier = modifier,
  ) { innerPadding ->
    val layoutDirection = LocalLayoutDirection.current
    ContentContainer(
      modifier =
        Modifier.fillMaxWidth()
          .padding(
            top = innerPadding.calculateTopPadding(),
            start = innerPadding.calculateLeftPadding(layoutDirection),
            end = innerPadding.calculateRightPadding(layoutDirection),
          )
    ) {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(MarginSingle),
      ) {
        ThemePreference(
          selectedTheme = state.theme,
          onThemeSelected = { state.eventSink(SettingsUiEvent.ThemeChanged(it)) },
          modifier = Modifier.fillMaxWidth().padding(horizontal = MarginDouble),
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          HorizontalDivider(
            modifier =
              Modifier.padding(
                vertical = MarginSingle,
                horizontal = MarginDouble,
              )
          )

          SwitchPreference(
            title = stringResource(R.string.settings_dynamic_color_title),
            summary = stringResource(R.string.settings_dynamic_color_subtitle),
            checked = state.useDynamicTheme,
            onCheckedChange = { state.eventSink(SettingsUiEvent.DynamicThemeChanged(it)) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = MarginDouble),
          )
        }
        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
      }
    }
  }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun SettingsUiPreview() {
  NeoExplorerTheme {
    SettingsUi(
      state =
        SettingsUiState(
          isLoading = false,
          theme = AppTheme.AUTO,
          useDynamicTheme = false,
          eventSink = {},
        )
    )
  }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun SettingsUiDynamicThemePreview() {
  NeoExplorerTheme {
    SettingsUi(
      state =
        SettingsUiState(
          isLoading = false,
          theme = AppTheme.LIGHT,
          useDynamicTheme = true,
          eventSink = {},
        )
    )
  }
}

@WidgetPreviews
@Composable
private fun SettingsUiDarkThemePreview() {
  NeoExplorerTheme {
    SettingsUi(
      state =
        SettingsUiState(
          isLoading = false,
          theme = AppTheme.DARK,
          useDynamicTheme = false,
          eventSink = {},
        )
    )
  }
}
