package com.francesc.neoexplorer.ui.feature.details.components

import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidId
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Distance
import com.francesc.neoexplorer.ui.shared.compose.asteroid.Velocity

data class DetailsUiModel(
  val id: AsteroidId,
  val name: String,
  val isPotentiallyHazardous: Boolean,
  val diameterMinKm: Double,
  val diameterMaxKm: Double,
  val velocity: Velocity,
  val missDistance: Distance,
  val orbitingBody: String,
  val nasaJplUrl: String,
  val closeApproachDate: String,
  val sizeReference: SizeReferenceObject,
) {
  val velocityKmPerHour: Double
    get() = velocity.kmPerHour
}
