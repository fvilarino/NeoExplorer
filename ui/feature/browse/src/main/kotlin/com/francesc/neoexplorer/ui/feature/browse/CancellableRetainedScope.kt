package com.francesc.neoexplorer.ui.feature.browse

import androidx.compose.runtime.RememberObserver
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * A [CoroutineScope] wrapper that implements [RememberObserver], allowing it to be stored in
 * [com.slack.circuit.retained.rememberRetained].
 *
 * A raw [CoroutineScope] does **not** implement [RememberObserver], so without this wrapper the
 * scope would never be cancelled when the retained slot is evicted — leaking active coroutines for
 * every back-stack entry that was ever pushed.
 *
 * [rememberRetained] calls [onForgotten] on the stored value when it finally removes the entry
 * (e.g. when the back-stack entry is popped and the registry is cleared). This triggers
 * [CoroutineScope.cancel], cleaning up all child coroutines (e.g. the paging [cachedIn] flow).
 *
 * Usage:
 * ```
 * val retainedScope = rememberRetained("key") {
 *     CancellableRetainedScope(SupervisorJob() + dispatcherProvider.main)
 * }.scope
 * ```
 */
internal class CancellableRetainedScope(context: CoroutineContext) : RememberObserver {

  val scope: CoroutineScope = CoroutineScope(context)

  override fun onRemembered() = Unit

  /** Invoked when the retained slot is finally evicted (e.g. back-stack pop). */
  override fun onForgotten() = scope.cancel()

  /** Invoked if the object is abandoned before it was ever remembered. */
  override fun onAbandoned() = scope.cancel()
}
