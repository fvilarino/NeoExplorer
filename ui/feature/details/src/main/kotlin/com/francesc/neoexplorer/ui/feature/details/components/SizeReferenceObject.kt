package com.francesc.neoexplorer.ui.feature.details.components

import androidx.annotation.DrawableRes
import com.francesc.neoexplorer.ui.feature.details.R

/**
 * A known reference object used to give the asteroid's diameter a human-scale comparison.
 *
 * Objects are ordered from smallest to largest; the companion will select the smallest reference
 * whose [sizeMeters] is greater than or equal to the asteroid's max diameter.
 */
enum class SizeReferenceObject(
  val label: String,
  @DrawableRes val iconRes: Int,
  val sizeMeters: Double,
) {
  BICYCLE("Bicycle", R.drawable.bicycle, 1.7),
  CITY_BUS("City Bus", R.drawable.city_bus, 12.0),
  BOEING_747("Boeing 747", R.drawable.airplane, 70.0),
  FOOTBALL_FIELD("Football Field", R.drawable.football_field, 105.0),
  EMPIRE_STATE_BUILDING("Empire State Building", R.drawable.empire_state, 330.0),
  BURJ_KHALIFA("Burj Khalifa", R.drawable.burj_khalifa, 830.0),
  GOLDEN_GATE_BRIDGE("Golden Gate Bridge", R.drawable.golden_gate, 2737.0),
  CENTRAL_PARK("Central Park", R.drawable.central_park, 4000.0),
  MOUNT_EVEREST("Mount Everest", R.drawable.everest, 8849.0);

  companion object {
    /**
     * Returns the smallest reference object that is >= [diameterMeters]. Falls back to
     * [MOUNT_EVEREST] for very large asteroids.
     */
    fun from(diameterMeters: Double): SizeReferenceObject =
      entries.firstOrNull { it.sizeMeters >= diameterMeters } ?: MOUNT_EVEREST
  }
}
