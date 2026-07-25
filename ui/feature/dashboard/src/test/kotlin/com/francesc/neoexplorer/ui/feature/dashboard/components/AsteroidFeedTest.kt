package com.francesc.neoexplorer.ui.feature.dashboard.components

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidUiModel
import com.francesc.neoexplorer.ui.shared.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.asteroid.ThreatLevel
import com.francesc.neoexplorer.ui.shared.asteroid.Velocity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class AsteroidFeedTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val testDate = "25 Jun 2026"

  @Test
  fun asteroidFeed_showsHeaderInfo() {
    val date = LocalDate(2026, Month.JULY, 25)
    val hazardousCount = 5

    composeTestRule.render(
      date = testDate,
      hazardousCount = hazardousCount,
    )

    // Header date is formatted as "D Month Year"
    composeTestRule.onNodeWithText(testDate).assertIsDisplayed()
    composeTestRule
      .onNodeWithText(
        context.getString(R.string.potentially_hazardous_objects_today, hazardousCount)
      )
      .assertIsDisplayed()
  }

  @Test
  fun asteroidFeed_showsAsteroidList() {
    val asteroid =
      AsteroidUiModel(
        id = AsteroidId("1"),
        name = "Asteroid 1",
        absoluteMagnitudeH = 20.0,
        missDistance = Distance.km(1000.0),
        isPotentiallyHazardous = false,
        velocity = Velocity(10.0),
        estimatedDiameterMaxKm = 1.0,
        closeApproachDate = testDate,
        threatLevel = ThreatLevel.SAFE,
      )

    composeTestRule.render(asteroids = listOf(asteroid))

    composeTestRule.onNodeWithText("Asteroid 1").assertIsDisplayed()
  }

  @Test
  fun asteroidFeed_hazardousAsteroid_showsHazardousBadge() {
    val asteroid =
      AsteroidUiModel(
        id = AsteroidId("1"),
        name = "Hazardous Asteroid",
        absoluteMagnitudeH = 20.0,
        missDistance = Distance.km(1000.0),
        isPotentiallyHazardous = true,
        velocity = Velocity(10.0),
        estimatedDiameterMaxKm = 1.0,
        closeApproachDate = testDate,
        threatLevel = ThreatLevel.DANGER,
      )

    composeTestRule.render(
      asteroids = listOf(asteroid),
      hazardousCount = 1,
    )

    composeTestRule.onNodeWithText("Hazardous Asteroid").assertIsDisplayed()
    composeTestRule
      .onNodeWithContentDescription(
        context.getString(
          com.francesc.neoexplorer.ui.shared.compose.R.string.accessibility_potentially_hazardous
        )
      )
      .assertIsDisplayed()
  }

  @Test
  fun asteroidFeed_onAsteroidClick_triggersCallback() {
    var clickedId: AsteroidId? = null
    val asteroidId = AsteroidId("1")
    val asteroid =
      AsteroidUiModel(
        id = asteroidId,
        name = "Asteroid 1",
        absoluteMagnitudeH = 20.0,
        missDistance = Distance.km(1000.0),
        isPotentiallyHazardous = false,
        velocity = Velocity(10.0),
        estimatedDiameterMaxKm = 1.0,
        closeApproachDate = testDate,
        threatLevel = ThreatLevel.SAFE,
      )

    composeTestRule.render(
      asteroids = listOf(asteroid),
      onAsteroidClick = { clickedId = it },
    )

    composeTestRule.onNodeWithText("Asteroid 1").performClick()
    assertEquals(asteroidId, clickedId)
  }

  @Test
  fun asteroidFeed_emptyList_showsEmptyStateMessage() {
    val emptyMessage = context.getString(R.string.no_close_approaches_today)

    composeTestRule.render()

    composeTestRule.onNodeWithText(emptyMessage).assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(
    asteroids: List<AsteroidUiModel> = emptyList(),
    date: String = "25 Jun 2026",
    hazardousCount: Int = 0,
    onAsteroidClick: (AsteroidId) -> Unit = {},
  ) {
    setContent {
      AsteroidFeed(
        asteroids = asteroids,
        date = date,
        hazardousCount = hazardousCount,
        onAsteroidClick = onAsteroidClick,
      )
    }
  }
}
