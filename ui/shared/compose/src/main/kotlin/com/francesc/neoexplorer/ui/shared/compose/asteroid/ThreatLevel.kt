package com.francesc.neoexplorer.ui.shared.compose.asteroid

enum class ThreatLevel {
  SAFE,
  CAUTION,
  DANGER;

  companion object {
    fun from(missDistanceLunar: Double): ThreatLevel =
      when {
        missDistanceLunar < 5.0 -> DANGER
        missDistanceLunar <= 15.0 -> CAUTION
        else -> SAFE
      }
  }
}
