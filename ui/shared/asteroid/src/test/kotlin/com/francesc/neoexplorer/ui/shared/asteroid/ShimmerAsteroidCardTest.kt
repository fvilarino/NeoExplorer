package com.francesc.neoexplorer.ui.shared.asteroid

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class ShimmerAsteroidCardTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun shimmerAsteroidCard_isDisplayed() {
    composeTestRule.render()

    composeTestRule.onNodeWithTag(ShimmerAsteroidCardTag).assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render() {
    setContent {
      NeoExplorerTheme {
        ShimmerAsteroidCard()
      }
    }
  }
}
