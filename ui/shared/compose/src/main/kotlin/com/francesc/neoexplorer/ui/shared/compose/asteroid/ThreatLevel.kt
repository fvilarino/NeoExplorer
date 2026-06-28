package com.francesc.neoexplorer.ui.shared.compose.asteroid

enum class ThreatLevel {
  SAFE,
  CAUTION,
  DANGER;

  companion object {
    fun from(missDistance: Distance): ThreatLevel =
      when {
        !missDistance.isKnown -> SAFE
        missDistance.inLunarDistances < 5.0 -> DANGER
        missDistance.inLunarDistances <= 15.0 -> CAUTION
        else -> SAFE
      }
  }
}
