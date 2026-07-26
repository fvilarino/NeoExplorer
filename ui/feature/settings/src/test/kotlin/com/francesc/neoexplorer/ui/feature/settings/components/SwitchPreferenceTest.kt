package com.francesc.neoexplorer.ui.feature.settings.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class SwitchPreferenceTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun switchPreference_displaysTitleAndSummary() {
    val title = "Switch Title"
    val summary = "Switch Summary"
    composeTestRule.render(title = title, summary = summary, checked = true, onCheckedChange = {})

    composeTestRule.onNodeWithText(title).assertIsDisplayed()
    composeTestRule.onNodeWithText(summary).assertIsDisplayed()
  }

  @Test
  fun switchPreference_displaysCheckedState() {
    composeTestRule.render(title = "Title", checked = true, onCheckedChange = {})

    composeTestRule.onNode(hasClickAction()).assertIsOn()
  }

  @Test
  fun switchPreference_displaysUncheckedState() {
    composeTestRule.render(title = "Title", checked = false, onCheckedChange = {})

    composeTestRule.onNode(hasClickAction()).assertIsOff()
  }

  @Test
  fun switchPreference_onClick_invokesLambda() {
    var checkedResult = false
    composeTestRule.render(
      title = "Title",
      checked = false,
      onCheckedChange = { checkedResult = it },
    )

    composeTestRule.onNode(hasClickAction()).performClick()

    assertTrue(checkedResult)
  }

  private fun ComposeContentTestRule.render(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    summary: String? = null,
  ) {
    setContent {
      NeoExplorerTheme {
        SwitchPreference(
          title = title,
          checked = checked,
          onCheckedChange = onCheckedChange,
          summary = summary,
        )
      }
    }
  }
}
