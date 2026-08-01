package com.francesc.neoexplorer.ui.shared.asteroid

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.compose.R
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class AsteroidFeedTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  @Test
  fun asteroidFeed_whenEmpty_displaysEmptyMessage() {
    val emptyMessage = "No asteroids today"
    composeTestRule.render(asteroids = emptyList(), emptyStateMessage = emptyMessage)

    composeTestRule.onNodeWithText(emptyMessage).assertIsDisplayed()
  }

  @Test
  fun asteroidFeed_whenEmpty_displaysDefaultMessage_ifNoneProvided() {
    composeTestRule.render(asteroids = emptyList(), emptyStateMessage = null)

    composeTestRule
      .onNodeWithText(context.getString(R.string.no_asteroids_in_range))
      .assertIsDisplayed()
  }

  @Test
  fun asteroidFeed_displaysHeader() {
    val headerText = "Header content"
    composeTestRule.render(
      asteroids = emptyList(),
      header = { Text(headerText) },
    )

    composeTestRule.onNodeWithText(headerText).assertIsDisplayed()
  }

  @Test
  fun asteroidFeed_displaysAsteroids() {
    val asteroids =
      listOf(
        createAsteroid(id = "1", name = "Asteroid 1"),
        createAsteroid(id = "2", name = "Asteroid 2"),
      )
    composeTestRule.render(asteroids = asteroids)

    composeTestRule.onNodeWithText("Asteroid 1").assertIsDisplayed()
    composeTestRule.onNodeWithText("Asteroid 2").assertIsDisplayed()
  }

  @Test
  fun asteroidFeed_onAsteroidClick_triggersLambda() {
    val asteroidId = AsteroidId("1")
    val asteroids = listOf(createAsteroid(id = asteroidId.value, name = "Asteroid 1"))
    var clickedId: AsteroidId? = null
    composeTestRule.render(
      asteroids = asteroids,
      onAsteroidClick = { clickedId = it },
    )

    composeTestRule.onNodeWithText("Asteroid 1").performClick()

    assert(clickedId == asteroidId)
  }

  private fun ComposeContentTestRule.render(
    asteroids: List<AsteroidUiModel> = emptyList(),
    onAsteroidClick: (AsteroidId) -> Unit = {},
    header: @Composable () -> Unit = {},
    emptyStateMessage: String? = null,
  ) {
    setContent {
      NeoExplorerTheme {
        AsteroidFeed(
          asteroids = asteroids,
          onAsteroidClick = onAsteroidClick,
          header = header,
          emptyStateMessage = emptyStateMessage,
        )
      }
    }
  }

  private fun createAsteroid(
    id: String = "id",
    name: String = "Test Asteroid",
  ) =
    AsteroidUiModel(
      id = AsteroidId(id),
      name = name,
      absoluteMagnitudeH = 20.0,
      missDistance = Distance.km(1000000.0),
      isPotentiallyHazardous = false,
      velocity = Velocity(10.0),
      estimatedDiameterMaxKm = 0.1,
      closeApproachDate = "2024-01-01",
      threatLevel = ThreatLevel.SAFE,
    )
}
