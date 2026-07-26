package com.francesc.neoexplorer.ui.feature.settings.components

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.data.preferences.AppTheme
import com.francesc.neoexplorer.ui.feature.settings.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class ThemePreferenceTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Application>()

  @Test
  fun themePreference_displaysTitle() {
    composeTestRule.render(selectedTheme = AppTheme.AUTO, onThemeSelected = {})

    composeTestRule
      .onNodeWithText(context.getString(R.string.settings_theme_section))
      .assertIsDisplayed()
  }

  @Test
  fun themePreference_displaysThemes() {
    composeTestRule.render(selectedTheme = AppTheme.AUTO, onThemeSelected = {})

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.settings_theme_auto))
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.settings_theme_light))
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.settings_theme_dark))
      .assertIsDisplayed()
  }

  @Test
  fun themePreference_showsSelection() {
    composeTestRule.render(selectedTheme = AppTheme.DARK, onThemeSelected = {})

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.settings_theme_dark))
      .assertIsOn()
    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.settings_theme_auto))
      .assertIsOff()
    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.settings_theme_light))
      .assertIsOff()
  }

  @Test
  fun themePreference_onThemeSelected_invokesLambda() {
    var selected: AppTheme? = null
    composeTestRule.render(
      selectedTheme = AppTheme.AUTO,
      onThemeSelected = { selected = it },
    )

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.settings_theme_light))
      .performClick()

    assertEquals(AppTheme.LIGHT, selected)
  }

  private fun ComposeContentTestRule.render(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
  ) {
    setContent {
      NeoExplorerTheme {
        ThemePreference(
          selectedTheme = selectedTheme,
          onThemeSelected = onThemeSelected,
        )
      }
    }
  }
}
