package com.francesc.neoexplorer.ui.shared.navigation

import kotlinx.coroutines.flow.Flow

/** Exposes in-app navigation events to the Compose navigation layer. */
interface NavigationRouter {
    /** A [Flow] broadcasting each [NavigationPayload] emitted by a [NavigationBroadcaster]. */
    val events: Flow<NavigationPayload>
}
