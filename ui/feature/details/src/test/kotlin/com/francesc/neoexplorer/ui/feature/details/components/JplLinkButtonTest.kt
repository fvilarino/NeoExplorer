package com.francesc.neoexplorer.ui.feature.details.components

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class JplLinkButtonTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Application>()

  @Test
  fun jplLinkButton_displaysText() {
    composeTestRule.render(onOpen = {})

    composeTestRule.onNodeWithText(context.getString(R.string.open_jpl_button)).assertIsDisplayed()
  }

  @Test
  fun jplLinkButton_onClick_invokesLambda() {
    var clicked = false
    composeTestRule.render(onOpen = { clicked = true })

    composeTestRule.onNodeWithText(context.getString(R.string.open_jpl_button)).performClick()

    assertTrue(clicked)
  }

  private fun ComposeContentTestRule.render(onOpen: () -> Unit) {
    setContent {
      NeoExplorerTheme {
        JplLinkButton(onOpen = onOpen)
      }
    }
  }
}
