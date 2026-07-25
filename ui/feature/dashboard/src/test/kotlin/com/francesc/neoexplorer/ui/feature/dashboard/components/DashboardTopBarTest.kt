package com.francesc.neoexplorer.ui.feature.dashboard.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.feature.dashboard.SortOrder
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class DashboardTopBarTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  @Test
  fun dashboardTopBar_displaysTitle() {
    composeTestRule.render()

    composeTestRule.onNodeWithText(context.getString(R.string.asteroid_watch)).assertIsDisplayed()
  }

  @Test
  fun dashboardTopBar_isNotLoaded_doesNotDisplaySortOptions() {
    composeTestRule.render(isLoaded = false)

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_sort_by_date))
      .assertDoesNotExist()

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_sort_by_distance))
      .assertDoesNotExist()
  }

  @Test
  fun dashboardTopBar_isLoaded_displaysSortOptions() {
    composeTestRule.render(isLoaded = true)

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_sort_by_date))
      .assertIsDisplayed()

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_sort_by_distance))
      .assertIsDisplayed()
  }

  @Test
  fun dashboardTopBar_selectsCorrectSortOrder_byDate() {
    composeTestRule.render(isLoaded = true, sortOrder = SortOrder.BY_DATE)

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_sort_by_date))
      .assertIsSelected()
  }

  @Test
  fun dashboardTopBar_selectsCorrectSortOrder_byDistance() {
    composeTestRule.render(isLoaded = true, sortOrder = SortOrder.BY_DISTANCE)

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_sort_by_distance))
      .assertIsSelected()
  }

  @Test
  fun dashboardTopBar_onSortOrderChange_triggered() {
    var capturedSortOrder: SortOrder? = null
    composeTestRule.render(
      isLoaded = true,
      sortOrder = SortOrder.BY_DATE,
      onSortOrderChange = { capturedSortOrder = it },
    )

    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_sort_by_distance))
      .performClick()

    assertEquals(SortOrder.BY_DISTANCE, capturedSortOrder)
  }

  private fun ComposeContentTestRule.render(
    isLoaded: Boolean = true,
    sortOrder: SortOrder = SortOrder.BY_DATE,
    onSortOrderChange: (SortOrder) -> Unit = {},
  ) {
    setContent {
      DashboardTopBar(
        isLoaded = isLoaded,
        sortOrder = sortOrder,
        onSortOrderChange = onSortOrderChange,
      )
    }
  }
}
