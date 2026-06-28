package com.francesc.neoexplorer.core.dispather.impl

import com.francesc.neoexplorer.core.dispather.DispatcherProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Suppress("InjectDispatcher")
internal object DispatcherProviderImpl : DispatcherProvider {

  override val default: CoroutineContext
    get() = Dispatchers.Default

  override val io: CoroutineContext
    get() = Dispatchers.IO

  override val main: CoroutineContext
    get() = Dispatchers.Main

  override val unconfined: CoroutineContext
    get() = Dispatchers.Unconfined
}
