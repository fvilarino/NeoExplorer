package com.francesc.neoexplorer.ui.shared.navigation

import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@SingleIn(AppScope::class)
@Inject
class NavigationRouterImpl : NavigationBroadcaster, NavigationRouter {

  private val _events = MutableSharedFlow<NavigationPayload>(extraBufferCapacity = 1)

  override val events: Flow<NavigationPayload> = _events

  override fun broadcast(screen: Screen) {
    _events.tryEmit(NavigationPayload(screens = listOf(screen)))
  }
}

@ContributesTo(AppScope::class)
interface NavigationBindingsModule {
  @Binds fun bindsNavigationBroadcaster(impl: NavigationRouterImpl): NavigationBroadcaster

  @Binds fun bindsNavigationRouter(impl: NavigationRouterImpl): NavigationRouter
}
