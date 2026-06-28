package com.francesc.neoexplorer.ui.feature.details.components

internal fun formatKm(km: Double): String =
  when {
    km < 0.001 -> "${"%.1f".format(km * 1_000_000)} mm"
    km < 1.0 -> "${"%.0f".format(km * 1_000)} m"
    else -> "${"%.3f".format(km)} km"
  }

internal fun formatDistanceKm(km: Double): String =
  when {
    km >= 1_000_000.0 -> "${"%.2f".format(km / 1_000_000.0)} M km"
    else -> "${"%.0f".format(km / 1_000.0)} K km"
  }
