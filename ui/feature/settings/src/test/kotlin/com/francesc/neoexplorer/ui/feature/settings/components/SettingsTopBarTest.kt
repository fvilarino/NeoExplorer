package com.francesc.neoexplorer.ui.feature.settings.components

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.settings.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class SettingsTopBarTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Application>()

  @Test
  fun settingsTopBar_displaysTitle() {
    composeTestRule.render()

    composeTestRule.onNodeWithText(context.getString(R.string.settings)).assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render() {
    setContent {
      NeoExplorerTheme {
        SettingsTopBar()
      }
    }
  }
}
