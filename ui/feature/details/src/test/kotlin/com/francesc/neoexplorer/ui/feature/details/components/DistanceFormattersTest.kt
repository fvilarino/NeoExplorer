package com.francesc.neoexplorer.ui.feature.details.components

import com.francesc.neoexplorer.ui.shared.asteroid.Distance
import org.junit.Assert.assertEquals
import org.junit.Test

class DistanceFormattersTest {

  @Test
  fun formatKm_smallDistances_lessThanOneMillimeter() {
    assertEquals("0.5 mm", formatKm(0.0000005))
  }

  @Test
  fun formatKm_smallDistances_inMillimeters() {
    assertEquals("500.0 mm", formatKm(0.0005))
  }

  @Test
  fun formatKm_mediumDistances_inMeters() {
    assertEquals("500 m", formatKm(0.5))
  }

  @Test
  fun formatKm_largeDistances_inKilometers() {
    assertEquals("1.235 km", formatKm(1.234567))
  }

  @Test
  fun formatDistanceKm_unknownDistance() {
    assertEquals("–", formatDistanceKm(Distance.UNKNOWN))
  }

  @Test
  fun formatDistanceKm_millionsOfKilometers() {
    assertEquals("1.50 M km", formatDistanceKm(Distance.km(1_500_000.0)))
  }

  @Test
  fun formatDistanceKm_thousandsOfKilometers() {
    assertEquals("400 K km", formatDistanceKm(Distance.km(400_000.0)))
  }
}
