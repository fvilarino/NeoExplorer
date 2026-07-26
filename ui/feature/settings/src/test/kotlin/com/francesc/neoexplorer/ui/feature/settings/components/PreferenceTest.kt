package com.francesc.neoexplorer.ui.feature.settings.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class PreferenceTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun preference_displaysTitle() {
    val title = "Preference Title"
    composeTestRule.render(title = title)

    composeTestRule.onNodeWithText(title).assertIsDisplayed()
  }

  @Test
  fun preference_displaysSummary_whenProvided() {
    val summary = "Summary Text"
    composeTestRule.render(
      title = "Title",
      summary = { Text(text = summary) },
    )

    composeTestRule.onNodeWithText(summary).assertIsDisplayed()
  }

  @Test
  fun preference_displaysControl_whenProvided() {
    val controlText = "Control Text"
    composeTestRule.render(
      title = "Title",
      control = { Text(text = controlText) },
    )

    composeTestRule.onNodeWithText(controlText).assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(
    title: String,
    summary: (@Composable () -> Unit)? = null,
    control: (@Composable () -> Unit)? = null,
  ) {
    setContent {
      NeoExplorerTheme {
        Preference(
          title = title,
          summary = summary,
          control = control,
        )
      }
    }
  }
}
