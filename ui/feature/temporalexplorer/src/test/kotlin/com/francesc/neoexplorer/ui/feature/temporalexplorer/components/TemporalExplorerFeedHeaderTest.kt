package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.temporalexplorer.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class TemporalExplorerFeedHeaderTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Application>()

  @Test
  fun temporalExplorerFeedHeader_displaysDateRange() {
    val startDate = "20 Jul 2026"
    val endDate = "26 Jul 2026"

    composeTestRule.render(startDate = startDate, endDate = endDate, hazardousCount = 0)

    val expectedText =
      context.getString(
        R.string.temporal_explorer_date_range_header,
        startDate,
        endDate,
      )
    composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
  }

  @Test
  fun temporalExplorerFeedHeader_displaysHazardousCount() {
    val hazardousCount = 5
    composeTestRule.render(
      startDate = "20 Jul 2026",
      endDate = "26 Jul 2026",
      hazardousCount = hazardousCount,
    )

    val expectedText =
      context.getString(R.string.temporal_explorer_hazardous_objects, hazardousCount)
    composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(
    startDate: String,
    endDate: String,
    hazardousCount: Int,
  ) {
    setContent {
      NeoExplorerTheme {
        TemporalExplorerFeedHeader(
          startDate = startDate,
          endDate = endDate,
          hazardousCount = hazardousCount,
        )
      }
    }
  }
}
