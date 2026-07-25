package com.francesc.neoexplorer.ui.feature.dashboard.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.dashboard.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class AsteroidFeedHeaderTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  private val testDate = "25 Jun 2026"

  @Test
  fun asteroidFeedHeader_displaysFormattedDateAndHazardousCount() {
    composeTestRule.render(
      date = testDate,
      hazardousCount = 3,
    )

    // Verify the date header text is rendered
    composeTestRule.onNodeWithText(testDate).assertIsDisplayed()

    composeTestRule
      .onNodeWithText(context.getString(R.string.potentially_hazardous_objects_today, 3))
      .assertIsDisplayed()
  }

  @Test
  fun asteroidFeedHeader_zeroHazardousObjects_rendersCorrectly() {
    composeTestRule.render(
      date = testDate,
      hazardousCount = 0,
    )

    composeTestRule
      .onNodeWithText(context.getString(R.string.potentially_hazardous_objects_today, 0))
      .assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(
    date: String,
    hazardousCount: Int,
  ) {
    setContent {
      AsteroidFeedHeader(
        date = date,
        hazardousCount = hazardousCount,
      )
    }
  }
}
