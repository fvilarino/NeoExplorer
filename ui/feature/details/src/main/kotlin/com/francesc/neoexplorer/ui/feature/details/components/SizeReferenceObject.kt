package com.francesc.neoexplorer.ui.feature.details.components

/**
 * A known reference object used to give the asteroid's diameter a human-scale comparison.
 *
 * Objects are ordered from smallest to largest; the companion will select the smallest
 * reference whose [sizeKm] is greater than or equal to the asteroid's max diameter.
 */
enum class SizeReferenceObject(
    val label: String,
    val sizeKm: Double,
) {
    HUMAN("Average Human", 0.0017),
    SCHOOL_BUS("School Bus", 0.012),
    FOOTBALL_PITCH("Football Pitch", 0.105),
    EIFFEL_TOWER("Eiffel Tower", 0.330),
    BURJ_KHALIFA("Burj Khalifa", 0.830),
    CENTRAL_PARK("Central Park (length)", 4.0),
    MOUNT_EVEREST("Mount Everest (height)", 8.849);

    companion object {
        /**
         * Returns the smallest reference object that is >= [diameterKm].
         * Falls back to [MOUNT_EVEREST] for very large asteroids.
         */
        fun from(diameterKm: Double): SizeReferenceObject =
            entries.firstOrNull { it.sizeKm >= diameterKm } ?: MOUNT_EVEREST
    }
}
