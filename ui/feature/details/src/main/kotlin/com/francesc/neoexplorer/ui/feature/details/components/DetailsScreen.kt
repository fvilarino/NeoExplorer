package com.francesc.neoexplorer.ui.feature.details.components

import android.os.Parcelable
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidId
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize data class DetailsScreen(val asteroidId: AsteroidId) : Screen, Parcelable
