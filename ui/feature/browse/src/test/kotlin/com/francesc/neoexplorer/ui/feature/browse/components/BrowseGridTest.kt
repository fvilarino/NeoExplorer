package com.francesc.neoexplorer.ui.feature.browse.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidUiModel
import com.francesc.neoexplorer.ui.shared.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.asteroid.ThreatLevel
import com.francesc.neoexplorer.ui.shared.asteroid.Velocity
import com.francesc.neoexplorer.ui.shared.compose.R
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class BrowseGridTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  private val testAsteroid =
    AsteroidUiModel(
      id = AsteroidId("1"),
      name = "Asteroid 1",
      absoluteMagnitudeH = 20.0,
      missDistance = Distance.km(1000.0),
      isPotentiallyHazardous = false,
      velocity = Velocity(10.0),
      estimatedDiameterMaxKm = 1.0,
      closeApproachDate = "25 Jun 2026",
      threatLevel = ThreatLevel.SAFE,
    )

  @Test
  fun browseGrid_displaysAsteroids() {
    composeTestRule.render(pagingData = PagingData.from(listOf(testAsteroid)))

    composeTestRule.onNodeWithText("Asteroid 1").assertIsDisplayed()
  }

  @Test
  fun browseGrid_onAsteroidClick_triggered() {
    var clickedId: AsteroidId? = null
    val testAsteroid2 =
      testAsteroid.copy(
        id = AsteroidId("2"),
        name = "Asteroid 2",
      )
    composeTestRule.render(
      pagingData = PagingData.from(listOf(testAsteroid, testAsteroid2)),
      onAsteroidClick = { clickedId = it },
    )

    composeTestRule.onNodeWithText("Asteroid 1").performClick()
    assertEquals(testAsteroid.id, clickedId)
  }

  @Test
  fun browseGrid_hazardousAsteroid_showsHazardousBadge() {
    val hazardousAsteroid =
      testAsteroid.copy(
        id = AsteroidId("2"),
        name = "Hazardous Asteroid",
        isPotentiallyHazardous = true,
        threatLevel = ThreatLevel.DANGER,
      )

    composeTestRule.render(pagingData = PagingData.from(listOf(hazardousAsteroid)))

    composeTestRule.onNodeWithText("Hazardous Asteroid").assertIsDisplayed()
    composeTestRule
      .onNodeWithContentDescription(context.getString(R.string.accessibility_potentially_hazardous))
      .assertIsDisplayed()
  }

  @Test
  fun browseGrid_loadingAppend_showsProgressIndicator() {
    val loadingStates =
      LoadStates(
        refresh = LoadState.NotLoading(false),
        prepend = LoadState.NotLoading(false),
        append = LoadState.Loading,
      )
    composeTestRule.render(pagingData = PagingData.from(listOf(testAsteroid), loadingStates))

    composeTestRule.onNodeWithText("Asteroid 1").assertIsDisplayed()
    composeTestRule.onNodeWithTag(LoadingFooterTestTag).assertIsDisplayed()
  }

  @Test
  fun browseGrid_errorAppend_showsErrorContent() {
    val errorMessage = "API Error"
    val errorStates =
      LoadStates(
        refresh = LoadState.NotLoading(false),
        prepend = LoadState.NotLoading(false),
        append = LoadState.Error(RuntimeException(errorMessage)),
      )
    composeTestRule.render(pagingData = PagingData.from(listOf(testAsteroid), errorStates))

    composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    composeTestRule
      .onNodeWithText(context.getString(com.francesc.neoexplorer.ui.shared.compose.R.string.retry))
      .assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(
    pagingData: PagingData<AsteroidUiModel> = PagingData.empty(),
    onAsteroidClick: (AsteroidId) -> Unit = {},
  ) {
    setContent {
      val asteroids = flowOf(pagingData).collectAsLazyPagingItems()
      BrowseGrid(
        asteroids = asteroids,
        onAsteroidClick = onAsteroidClick,
      )
    }
  }
}
