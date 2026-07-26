package com.francesc.neoexplorer.ui.feature.details.components

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
class SectionTitleTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun sectionTitle_displaysText() {
    val title = "Section Title"
    composeTestRule.render(text = title)

    composeTestRule.onNodeWithText(title).assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(text: String) {
    setContent {
      NeoExplorerTheme {
        SectionTitle(text = text)
      }
    }
  }
}
