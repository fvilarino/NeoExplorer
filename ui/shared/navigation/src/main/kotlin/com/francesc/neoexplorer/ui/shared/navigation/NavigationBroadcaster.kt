package com.francesc.neoexplorer.ui.shared.navigation

import com.slack.circuit.runtime.screen.Screen

/** Broadcasts in-app navigation requests to the Circuit navigator. */
interface NavigationBroadcaster {
  fun broadcast(screen: Screen)
}
