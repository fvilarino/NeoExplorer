package com.francesc.neoexplorer.ui.feature.dashboard.components

import com.francesc.neoexplorer.data.neo.model.LunarDistances

enum class ThreatLevel {
    SAFE,
    CAUTION,
    DANGER;

    companion object {
        fun from(missDistanceLunar: LunarDistances): ThreatLevel = when {
            missDistanceLunar.value < 5.0 -> DANGER
            missDistanceLunar.value <= 15.0 -> CAUTION
            else -> SAFE
        }
    }
}
