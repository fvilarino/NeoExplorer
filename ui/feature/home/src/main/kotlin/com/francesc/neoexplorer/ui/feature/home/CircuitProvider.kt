package com.francesc.neoexplorer.ui.feature.home

import com.francesc.neoexplorer.ui.shared.navigation.NavigationRouter
import com.slack.circuit.foundation.Circuit

interface CircuitProvider {
    val circuit: Circuit
    val navigationRouter: NavigationRouter
}
