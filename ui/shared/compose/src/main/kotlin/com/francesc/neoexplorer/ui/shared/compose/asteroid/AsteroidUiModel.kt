package com.francesc.neoexplorer.ui.shared.compose.asteroid

data class AsteroidUiModel(
  val id: AsteroidId,
  val name: String,
  val absoluteMagnitudeH: Double,
  val missDistance: Distance,
  val isPotentiallyHazardous: Boolean,
  val velocity: Velocity,
  val estimatedDiameterMaxKm: Double,
  val closeApproachDate: String,
  val threatLevel: ThreatLevel,
)
