package com.francesc.neoexplorer.ui.feature.details.components

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.asteroid.Velocity
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35], qualifiers = "w400dp-h800dp")
class MetricsGridTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context = ApplicationProvider.getApplicationContext<Application>()

  private val uiModel =
    DetailsUiModel(
      id = AsteroidId("1"),
      name = "Asteroid 1",
      isPotentiallyHazardous = true,
      diameterMinKm = 0.25,
      diameterMaxKm = 0.42,
      velocity = Velocity(10.0),
      missDistance = Distance.km(1_250_000.0),
      orbitingBody = "Earth",
      nasaJplUrl = "https://ssd.jpl.nasa.gov/sbdb.cgi?sstr=1",
      closeApproachDate = "25 Jun 2026",
      sizeReference = SizeReferenceObject.BURJ_KHALIFA,
    )

  @Test
  fun metricsGrid_displaysAllMetrics() {
    composeTestRule.render(uiModel = uiModel)

    // Diameter Min
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_diameter_min))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("250 m").assertIsDisplayed()

    // Diameter Max
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_diameter_max))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("420 m").assertIsDisplayed()

    // Velocity
    composeTestRule.onNodeWithText(context.getString(R.string.metric_velocity)).assertIsDisplayed()
    composeTestRule.onNodeWithText("10.00 km/s").assertIsDisplayed()
    composeTestRule.onNodeWithText("36000 km/h").assertIsDisplayed()

    // Orbiting body
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_orbiting_body))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText(uiModel.orbitingBody).assertIsDisplayed()

    // Miss distance km
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_miss_distance_km))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("1.25 M km").assertIsDisplayed()

    // Miss distance lunar
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_miss_distance_lunar))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("3.25 LD").assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(
    uiModel: DetailsUiModel,
    columns: Int = 2,
  ) {
    setContent {
      NeoExplorerTheme {
        MetricsGrid(asteroid = uiModel, columns = columns)
      }
    }
  }
}
