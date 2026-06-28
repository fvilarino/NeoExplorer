package com.francesc.neoexplorer.core.dispather.impl

import com.francesc.neoexplorer.core.dispather.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

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

