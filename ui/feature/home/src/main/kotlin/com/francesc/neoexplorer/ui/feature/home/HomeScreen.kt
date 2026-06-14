package com.francesc.neoexplorer.ui.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.francesc.neoexplorer.ui.shared.navigation.NavigationRouter
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNot

@Composable
fun HomeScreen(
    circuit: Circuit,
    navigationRouter: NavigationRouter,
    modifier: Modifier = Modifier,
) {
    CircuitCompositionLocals(circuit) {
        val backStack = rememberSaveableBackStack(listOf(DashboardScreen))
        val navigator = rememberCircuitNavigator(backStack)

        LaunchedEffect(navigationRouter) {
            navigationRouter.events
                .filterNot { it.isConsumed }
                .collectLatest { payload ->
                    payload.consume()?.forEach { screen ->
                        navigator.goTo(screen)
                    }
                }
        }

        NavigableCircuitContent(
            navigator = navigator,
            backStack = backStack,
            modifier = modifier,
        )
    }
}
