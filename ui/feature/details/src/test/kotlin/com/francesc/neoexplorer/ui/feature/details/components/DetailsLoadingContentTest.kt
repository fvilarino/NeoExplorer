package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class DetailsLoadingContentTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  @Config(sdk = [35], qualifiers = "w400dp-h800dp")
  fun detailsLoadingContent_compact_rendersSingleColumn() {
    composeTestRule.setContent {
      DetailsLoadingContent()
    }

    // In single column, we have a LazyColumn.
    composeTestRule.onNode(hasScrollAction()).assertIsDisplayed()
  }

  @Test
  @Config(sdk = [35], qualifiers = "w900dp-h600dp")
  fun detailsLoadingContent_expanded_rendersTwoPane() {
    composeTestRule.setContent {
      DetailsLoadingContent()
    }

    // In two pane, we have two LazyColumns.
    composeTestRule.onAllNodes(hasScrollAction()).assertCountEquals(2)
  }
}
