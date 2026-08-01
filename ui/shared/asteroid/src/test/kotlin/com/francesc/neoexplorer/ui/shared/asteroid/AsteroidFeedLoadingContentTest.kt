package com.francesc.neoexplorer.ui.shared.asteroid

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35], qualifiers = "w1000dp-h2000dp")
class AsteroidFeedLoadingContentTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun asteroidFeedLoadingContent_displaysHeader_whenRequested() {
    composeTestRule.render(displayHeader = true)

    composeTestRule.onNodeWithTag(ShimmerFeedHeaderTag).assertIsDisplayed()
  }

  @Test
  fun asteroidFeedLoadingContent_doesNotDisplayHeader_whenNotRequested() {
    composeTestRule.render(displayHeader = false)

    composeTestRule.onNodeWithTag(ShimmerFeedHeaderTag).assertDoesNotExist()
  }

  @Test
  fun asteroidFeedLoadingContent_displaysMultipleShimmerCards() {
    composeTestRule.render()

    // It renders 6 items in the items block
    composeTestRule.onAllNodesWithTag(ShimmerAsteroidCardTag).assertCountEquals(6)
  }

  private fun ComposeContentTestRule.render(displayHeader: Boolean = true) {
    setContent {
      NeoExplorerTheme {
        AsteroidFeedLoadingContent(displayHeader = displayHeader)
      }
    }
  }
}
