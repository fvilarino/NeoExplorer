package com.francesc.neoexplorer.ui.feature.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.francesc.neoexplorer.ui.feature.browse.components.BrowseScreen
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.francesc.neoexplorer.ui.feature.settings.components.SettingsScreen
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerScreen
import com.francesc.neoexplorer.ui.shared.navigation.NavigationRouter
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNot

internal enum class HomeDestinations(
  @StringRes val label: Int,
  val icon: ImageVector,
  @StringRes val contentDescription: Int,
  val screen: Screen,
) {
  Dashboard(
    label = R.string.nav_dashboard,
    icon = Icons.Default.Home,
    contentDescription = R.string.nav_dashboard,
    screen = DashboardScreen,
  ),
  Browse(
    label = R.string.nav_browse,
    icon = Icons.Default.Public,
    contentDescription = R.string.nav_browse,
    screen = BrowseScreen,
  ),
  TemporalExplorer(
    label = R.string.nav_temporal_explorer,
    icon = Icons.Default.DateRange,
    contentDescription = R.string.nav_temporal_explorer,
    screen = TemporalExplorerScreen,
  ),
  Settings(
    label = R.string.nav_settings,
    icon = Icons.Default.Settings,
    contentDescription = R.string.nav_settings,
    screen = SettingsScreen,
  ),
}

@Composable
fun HomeScreen(
  circuit: Circuit,
  navigationRouter: NavigationRouter,
  modifier: Modifier = Modifier,
) {
  CircuitCompositionLocals(circuit) {
    var currentDestination by rememberSaveable { mutableStateOf(HomeDestinations.Dashboard) }
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

    NavigationSuiteScaffold(
      modifier = modifier,
      navigationSuiteItems = {
        HomeDestinations.entries.forEach { destination ->
          item(
            icon = {
              Icon(
                imageVector = destination.icon,
                contentDescription = stringResource(destination.contentDescription),
              )
            },
            label = {
              Text(
                text = stringResource(destination.label),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            },
            selected = destination == currentDestination,
            onClick = {
              if (currentDestination != destination) {
                currentDestination = destination
                navigator.resetRoot(destination.screen)
              }
            },
          )
        }
      },
    ) {
      ContentWithOverlays {
        NavigableCircuitContent(
          navigator = navigator,
          backStack = backStack,
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}
