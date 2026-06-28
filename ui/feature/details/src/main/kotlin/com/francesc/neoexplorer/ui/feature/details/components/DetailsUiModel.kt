package com.francesc.neoexplorer.ui.feature.details.components

data class DetailsUiModel(
  val id: String,
  val name: String,
  val isPotentiallyHazardous: Boolean,
  val diameterMinKm: Double,
  val diameterMaxKm: Double,
  val velocityKmPerSecond: Double,
  val missDistanceKm: Double,
  val missDistanceLunar: Double,
  val orbitingBody: String,
  val nasaJplUrl: String,
  val closeApproachDate: String,
  val sizeReference: SizeReferenceObject,
) {
  val velocityKmPerHour: Double
    get() = velocityKmPerSecond * 3_600.0
}
