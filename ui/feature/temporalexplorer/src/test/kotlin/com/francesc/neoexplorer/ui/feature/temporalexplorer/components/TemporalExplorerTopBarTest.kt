package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
class TemporalExplorerTopBarTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Application>()

  @Test
  fun temporalExplorerTopBar_displaysTitle() {
    composeTestRule.render(isLoaded = false, onSelectDateClick = {})

    composeTestRule
      .onNodeWithText(context.getString(R.string.temporal_explorer))
      .assertIsDisplayed()
  }

  @Test
  fun temporalExplorerTopBar_whenLoaded_displaysActionIcon() {
    composeTestRule.render(isLoaded = true, onSelectDateClick = {})

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.temporal_explorer_change_date_range))
      .assertIsDisplayed()
  }

  @Test
  fun temporalExplorerTopBar_whenNotLoaded_hidesActionIcon() {
    composeTestRule.render(isLoaded = false, onSelectDateClick = {})

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.temporal_explorer_change_date_range))
      .assertDoesNotExist()
  }

  @Test
  fun temporalExplorerTopBar_onClick_invokesLambda() {
    var clicked = false
    composeTestRule.render(isLoaded = true, onSelectDateClick = { clicked = true })

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.temporal_explorer_change_date_range))
      .performClick()

    assertTrue(clicked)
  }

  private fun ComposeContentTestRule.render(
    isLoaded: Boolean,
    onSelectDateClick: () -> Unit,
  ) {
    setContent {
      NeoExplorerTheme {
        TemporalExplorerTopBar(
          isLoaded = isLoaded,
          onSelectDateClick = onSelectDateClick,
        )
      }
    }
  }
}
