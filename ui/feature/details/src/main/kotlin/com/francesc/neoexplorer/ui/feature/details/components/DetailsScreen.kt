package com.francesc.neoexplorer.ui.feature.details.components

import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidId
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize data class DetailsScreen(val asteroidId: AsteroidId) : Screen
