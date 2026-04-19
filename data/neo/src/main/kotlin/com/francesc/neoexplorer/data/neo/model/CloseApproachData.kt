package com.francesc.neoexplorer.data.neo.model

import kotlinx.datetime.LocalDate

data class CloseApproachData(
    val closeApproachDate: LocalDate,
    val relativeVelocityKmPerSecond: KilometersPerSecond,
    val missDistanceKm: Kilometers,
    val orbitingBody: String,
)
