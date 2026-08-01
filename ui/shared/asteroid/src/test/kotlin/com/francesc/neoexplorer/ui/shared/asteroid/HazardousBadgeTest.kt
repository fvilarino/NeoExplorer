package com.francesc.neoexplorer.ui.shared.asteroid

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.compose.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class HazardousBadgeTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  @Test
  fun hazardousBadge_displaysAbbreviation() {
    composeTestRule.render()

    composeTestRule
      .onNodeWithText(context.getString(R.string.potentially_hazardous_abbreviation))
      .assertIsDisplayed()
  }

  @Test
  fun hazardousBadge_displaysIconWithAccessibilityLabel() {
    composeTestRule.render()

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_potentially_hazardous))
      .assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render() {
    setContent {
      NeoExplorerTheme {
        HazardousBadge()
      }
    }
  }
}
