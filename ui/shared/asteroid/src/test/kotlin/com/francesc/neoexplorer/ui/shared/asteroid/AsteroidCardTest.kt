package com.francesc.neoexplorer.ui.shared.asteroid

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.compose.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class AsteroidCardTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  @Test
  fun asteroidCard_displaysName() {
    val name = "Asteroid 123"
    composeTestRule.render(asteroid = createAsteroid(name = name))

    composeTestRule.onNodeWithText(name).assertIsDisplayed()
  }

  @Test
  fun asteroidCard_displaysHazardousBadge_whenHazardous() {
    composeTestRule.render(asteroid = createAsteroid(isPotentiallyHazardous = true))

    composeTestRule
      .onNodeWithText(context.getString(R.string.potentially_hazardous_abbreviation))
      .assertIsDisplayed()
  }

  @Test
  fun asteroidCard_doesNotDisplayHazardousBadge_whenSafe() {
    composeTestRule.render(asteroid = createAsteroid(isPotentiallyHazardous = false))

    composeTestRule
      .onNodeWithText(context.getString(R.string.potentially_hazardous_abbreviation))
      .assertDoesNotExist()
  }

  @Test
  fun asteroidCard_displaysMissDistance() {
    // 5.0 LD -> 5.0 * 384400.0 = 1,922,000.0 km -> 1.9 M km
    val missDistance = Distance.lunar(5.0)
    composeTestRule.render(asteroid = createAsteroid(missDistance = missDistance))

    val expectedText =
      context.getString(
        R.string.asteroid_miss_distance,
        "5.0",
        "1.9 M km",
      )
    composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
  }

  @Test
  fun asteroidCard_displaysVelocity() {
    val velocity = Velocity(15.5)
    composeTestRule.render(asteroid = createAsteroid(velocity = velocity))

    val expectedText = context.getString(R.string.asteroid_velocity, "15.5")
    composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
  }

  @Test
  fun asteroidCard_displaysDiameter() {
    val diameter = 0.5 // 500m
    composeTestRule.render(asteroid = createAsteroid(estimatedDiameterMaxKm = diameter))

    composeTestRule.onNodeWithText("~500 m").assertIsDisplayed()
  }

  @Test
  fun asteroidCard_displaysCloseApproachDate() {
    val date = "2024-05-20"
    composeTestRule.render(asteroid = createAsteroid(closeApproachDate = date))

    composeTestRule.onNodeWithText(date).assertIsDisplayed()
  }

  @Test
  fun asteroidCard_onClick_triggersLambda() {
    var clicked = false
    composeTestRule.render(onClick = { clicked = true })

    composeTestRule.onNodeWithText("Test Asteroid").performClick()

    assertTrue(clicked)
  }

  private fun ComposeContentTestRule.render(
    asteroid: AsteroidUiModel = createAsteroid(),
    onClick: () -> Unit = {},
  ) {
    setContent {
      NeoExplorerTheme {
        AsteroidCard(
          asteroid = asteroid,
          onClick = onClick,
        )
      }
    }
  }

  private fun createAsteroid(
    name: String = "Test Asteroid",
    isPotentiallyHazardous: Boolean = false,
    missDistance: Distance = Distance.km(1000000.0),
    velocity: Velocity = Velocity(10.0),
    estimatedDiameterMaxKm: Double = 0.1,
    closeApproachDate: String = "2024-01-01",
    threatLevel: ThreatLevel = ThreatLevel.SAFE,
  ) =
    AsteroidUiModel(
      id = AsteroidId("id"),
      name = name,
      absoluteMagnitudeH = 20.0,
      missDistance = missDistance,
      isPotentiallyHazardous = isPotentiallyHazardous,
      velocity = velocity,
      estimatedDiameterMaxKm = estimatedDiameterMaxKm,
      closeApproachDate = closeApproachDate,
      threatLevel = threatLevel,
    )
}
