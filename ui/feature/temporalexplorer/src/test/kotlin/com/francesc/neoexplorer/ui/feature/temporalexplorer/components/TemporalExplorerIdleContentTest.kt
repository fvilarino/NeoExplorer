package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.temporalexplorer.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class TemporalExplorerIdleContentTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Application>()

  @Test
  fun temporalExplorerIdleContent_displaysDescription() {
    composeTestRule.render(onSelectDateClick = {})

    composeTestRule
      .onNodeWithText(context.getString(R.string.temporal_explorer_description))
      .assertIsDisplayed()
  }

  @Test
  fun temporalExplorerIdleContent_displaysButton() {
    composeTestRule.render(onSelectDateClick = {})

    composeTestRule
      .onNodeWithText(context.getString(R.string.temporal_explorer_select_date_range))
      .assertIsDisplayed()
  }

  @Test
  fun temporalExplorerIdleContent_onClick_invokesLambda() {
    var clicked = false
    composeTestRule.render(onSelectDateClick = { clicked = true })

    composeTestRule
      .onNodeWithText(context.getString(R.string.temporal_explorer_select_date_range))
      .performClick()

    assertTrue(clicked)
  }

  private fun ComposeContentTestRule.render(onSelectDateClick: () -> Unit) {
    setContent {
      NeoExplorerTheme {
        TemporalExplorerIdleContent(onSelectDateClick = onSelectDateClick)
      }
    }
  }
}
