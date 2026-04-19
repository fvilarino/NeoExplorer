package com.francesc.neoexplorer

import android.app.Application
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(AppScope::class)
interface ApplicationGraph {

    fun inject(application: NeoExplorerApplication)
    val circuit: Circuit

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides application: Application): ApplicationGraph
    }
}
