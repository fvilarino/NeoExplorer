package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class MetricCardTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun metricCard_displaysLabelAndValue() {
    val label = "Metric Label"
    val value = "Metric Value"
    composeTestRule.render(label = label, value = value)

    composeTestRule.onNodeWithText(label).assertIsDisplayed()
    composeTestRule.onNodeWithText(value).assertIsDisplayed()
  }

  @Test
  fun metricCard_displaysSubValue_whenProvided() {
    val subValue = "Sub Value"
    composeTestRule.render(label = "Label", value = "Value", subValue = subValue)

    composeTestRule.onNodeWithText(subValue).assertIsDisplayed()
  }

  private fun ComposeContentTestRule.render(
    label: String,
    value: String,
    subValue: String? = null,
  ) {
    setContent {
      NeoExplorerTheme {
        MetricCard(label = label, value = value, subValue = subValue)
      }
    }
  }
}
