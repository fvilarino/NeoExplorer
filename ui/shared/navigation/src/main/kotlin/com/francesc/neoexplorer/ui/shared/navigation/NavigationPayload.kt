package com.francesc.neoexplorer.ui.shared.navigation

import com.slack.circuit.runtime.screen.Screen
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A one-shot wrapper around a list of [Screen]s to navigate to.
 * Mirrors the DeeplinkPayload pattern from PasswordManagerCompose.
 */
class NavigationPayload(
    private val screens: List<Screen>,
) {
    private val consumed = AtomicBoolean(false)

    /** Whether this payload has already been consumed. */
    val isConsumed: Boolean
        get() = consumed.get()

    /**
     * Consumes the payload if not yet consumed.
     *
     * @return the list of [Screen]s to navigate to, or null if already consumed.
     */
    fun consume(): List<Screen>? = if (!consumed.getAndSet(true)) screens else null
}
