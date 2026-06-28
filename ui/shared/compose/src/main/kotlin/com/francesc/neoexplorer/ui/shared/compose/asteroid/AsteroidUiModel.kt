package com.francesc.neoexplorer.ui.shared.compose.asteroid

data class AsteroidUiModel(
  val id: String,
  val name: String,
  val absoluteMagnitudeH: Double,
  val missDistanceLunar: Double,
  val missDistanceKm: Double,
  val isPotentiallyHazardous: Boolean,
  val velocityKmPerSecond: Double,
  val estimatedDiameterMaxKm: Double,
  val closeApproachDate: String,
  val threatLevel: ThreatLevel,
)
