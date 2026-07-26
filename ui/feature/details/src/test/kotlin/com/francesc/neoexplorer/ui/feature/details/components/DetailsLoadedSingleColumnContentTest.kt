package com.francesc.neoexplorer.ui.feature.details.components

import android.app.Application
import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.asteroid.Velocity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35], qualifiers = "w400dp-h800dp")
class DetailsLoadedSingleColumnContentTest {

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
  fun detailsLoadedSingleColumnContent_displaysAsteroidInfo() {
    composeTestRule.render(uiModel = uiModel)
    composeTestRule
      .onNodeWithText(context.getString(R.string.section_object_characteristics))
      .assertIsDisplayed()

    // 1. Estimated diameter min
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_diameter_min))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("250 m").assertIsDisplayed()

    // 2. Estimated diameter max
    composeTestRule.onNodeWithText("420 m").assertIsDisplayed()
    composeTestRule.onNodeWithText(formatKm(uiModel.diameterMaxKm)).assertIsDisplayed()

    // 3. Relative velocity
    composeTestRule.onNodeWithText(context.getString(R.string.metric_velocity)).assertIsDisplayed()
    composeTestRule.onNodeWithText("10.00 km/s").assertIsDisplayed()
    composeTestRule.onNodeWithText("36000 km/h").assertIsDisplayed()

    // 4. Orbiting body
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_orbiting_body))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText(uiModel.orbitingBody).assertIsDisplayed()

    // 5. Miss distance km
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_miss_distance_km))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("1.25 M km").assertIsDisplayed()

    // 6. Miss distance lunar
    composeTestRule
      .onNodeWithText(context.getString(R.string.metric_miss_distance_lunar))
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("3.25 LD").assertIsDisplayed()
  }

  @Test
  fun detailsLoadedSingleColumnContent_displaysSizeComparison() {
    composeTestRule.render(uiModel = uiModel)
    composeTestRule
      .onNodeWithText(context.getString(R.string.size_comparison_title))
      .assertIsDisplayed()

    val asteroidInfo =
      "${context.getString(R.string.size_comparison_asteroid_label)} (${formatKm(uiModel.diameterMaxKm)})"
    composeTestRule.onNodeWithContentDescription(asteroidInfo).assertIsDisplayed()
  }

  @Test
  fun detailsLoadedSingleColumnContent_hazardous_displaysHazardousWarning() {
    composeTestRule.render(uiModel = uiModel.copy(isPotentiallyHazardous = true))
    composeTestRule
      .onNodeWithText(context.getString(R.string.potentially_hazardous_description))
      .assertIsDisplayed()
  }

  @Test
  fun detailsLoadedSingleColumnContent_jplButtonClick_startsActivity() {
    composeTestRule.render(uiModel = uiModel)

    composeTestRule.onNodeWithTag(DetailsSingleColumnContainerTestTag).performTouchInput {
      swipeUp(
        startY = bottom * 0.8f,
        endY = top * 0.2f,
      )
    }
    composeTestRule.onNodeWithText(context.getString(R.string.open_jpl_button)).performClick()

    val expectedIntent = Intent(Intent.ACTION_VIEW, uiModel.nasaJplUrl.toUri())
    val actualIntent = shadowOf(context).nextStartedActivity

    assertEquals(expectedIntent.action, actualIntent.action)
    assertEquals(expectedIntent.data, actualIntent.data)
  }

  private fun ComposeContentTestRule.render(
    uiModel: DetailsUiModel,
    horizontalPadding: Dp = 16.dp,
    contentPadding: PaddingValues = PaddingValues(),
  ) {
    setContent {
      DetailsLoadedSingleColumnContent(
        detailsUiModel = uiModel,
        horizontalPadding = horizontalPadding,
        contentPadding = contentPadding,
      )
    }
  }
}
