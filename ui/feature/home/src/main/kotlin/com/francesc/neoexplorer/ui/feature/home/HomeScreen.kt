package com.francesc.neoexplorer.ui.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent

@Composable
fun HomeScreen(circuit: Circuit, modifier: Modifier = Modifier) {
    CircuitCompositionLocals(circuit) {
        CircuitContent(
            screen = DashboardScreen,
            modifier = modifier,
        )
    }
}
