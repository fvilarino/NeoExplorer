package com.francesc.neoexplorer.ui.feature.details.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.details.R
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class DetailsErrorContentTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  @Test
  fun detailsErrorContent_displaysErrorMessage() {
    val message = "Error message"
    composeTestRule.render(message = message)

    composeTestRule.onNodeWithText(message).assertIsDisplayed()
  }

  @Test
  fun detailsErrorContent_displaysWarningIconWithAccessibilityLabel() {
    composeTestRule.render()

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_error))
      .assertIsDisplayed()
  }

  @Test
  fun detailsErrorContent_displaysRetryButton() {
    composeTestRule.render()

    composeTestRule.onNodeWithText(context.getString(R.string.retry)).assertIsDisplayed()
  }

  @Test
  fun detailsErrorContent_onRetryClick_triggered() {
    var retryClicked = false
    composeTestRule.render(onRetry = { retryClicked = true })

    composeTestRule.onNodeWithText(context.getString(R.string.retry)).performClick()

    assertTrue(retryClicked)
  }

  private fun ComposeContentTestRule.render(
    message: String = "Test Error",
    onRetry: () -> Unit = {},
  ) {
    setContent {
      DetailsErrorContent(
        message = message,
        onRetry = onRetry,
      )
    }
  }
}
