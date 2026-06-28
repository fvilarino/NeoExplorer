package com.francesc.neoexplorer.core.dispather.impl

import com.francesc.neoexplorer.core.dispather.DispatcherProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
interface DispatcherProviderGraph {
    val dispatcherProvider: DispatcherProvider

    @Provides
    @SingleIn(AppScope::class)
    @Suppress("InjectDispatcher")
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl
}

