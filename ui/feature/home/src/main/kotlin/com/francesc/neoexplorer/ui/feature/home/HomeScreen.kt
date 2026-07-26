package com.francesc.neoexplorer.ui.feature.home

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.feature.browse.components.BrowseScreen
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.francesc.neoexplorer.ui.feature.settings.components.SettingsScreen
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerScreen
import com.francesc.neoexplorer.ui.shared.compose.LocalHomeScaffoldPadding
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.screen.Screen

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
  modifier: Modifier = Modifier,
) {
  CircuitCompositionLocals(circuit) {
    var currentDestination by rememberSaveable { mutableStateOf(HomeDestinations.Dashboard) }
    val backStack = rememberSaveableBackStack(listOf(DashboardScreen))
    val navigator = rememberCircuitNavigator(backStack)

    var isNavigationBarVisible by retain { mutableStateOf(value = true) }

    val nestedScrollConnection = remember {
      object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
          if (available.y < -1f) {
            isNavigationBarVisible = false
          } else if (available.y > 1f) {
            isNavigationBarVisible = true
          }
          return Offset.Zero
        }
      }
    }

    LaunchedEffect(key1 = currentDestination) { isNavigationBarVisible = true }

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val navSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)

    val density = LocalDensity.current
    var measuredNavBarHeight by remember { mutableStateOf(0.dp) }

    val homePadding =
      if (navSuiteType == NavigationSuiteType.NavigationBar) {
        PaddingValues(bottom = measuredNavBarHeight)
      } else {
        PaddingValues()
      }

    CompositionLocalProvider(LocalHomeScaffoldPadding provides homePadding) {
      Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
      ) {
        val isBottomBar = navSuiteType == NavigationSuiteType.NavigationBar

        val content = remember {
          movableContentOf {
            ContentWithOverlays {
              NavigableCircuitContent(
                navigator = navigator,
                backStack = backStack,
                modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection),
              )
            }
          }
        }

        if (isBottomBar) {
          Box(modifier = Modifier.fillMaxSize()) {
            content()

            AnimatedVisibility(
              visible = isNavigationBarVisible,
              enter = slideInVertically { it },
              exit = slideOutVertically { it },
              modifier = Modifier.align(Alignment.BottomCenter),
            ) {
              NavigationSuite(
                layoutType = navSuiteType,
                modifier =
                  Modifier.onGloballyPositioned { coordinates ->
                    if (isNavigationBarVisible) {
                      val height = with(density) { coordinates.size.height.toDp() }
                      if (height > 0.dp) {
                        measuredNavBarHeight = height
                      }
                    }
                  },
              ) {
                homeDestinationItems(
                  currentDestination = currentDestination,
                  onDestinationClick = { destination ->
                    if (currentDestination != destination) {
                      currentDestination = destination
                      navigator.resetRoot(destination.screen)
                    }
                  },
                )
              }
            }
          }
        } else {
          NavigationSuiteScaffoldLayout(
            navigationSuiteType = navSuiteType,
            navigationSuite = {
              NavigationSuite(layoutType = navSuiteType) {
                homeDestinationItems(
                  currentDestination = currentDestination,
                  onDestinationClick = { destination ->
                    if (currentDestination != destination) {
                      currentDestination = destination
                      navigator.resetRoot(destination.screen)
                    }
                  },
                )
              }
            },
            content = content,
          )
        }
      }
    }
  }
}

private fun NavigationSuiteScope.homeDestinationItems(
  currentDestination: HomeDestinations,
  onDestinationClick: (HomeDestinations) -> Unit,
) {
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
      onClick = { onDestinationClick(destination) },
    )
  }
}
