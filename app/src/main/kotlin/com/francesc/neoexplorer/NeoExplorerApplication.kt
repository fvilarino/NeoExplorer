package com.francesc.neoexplorer

import android.app.Application
import com.francesc.neoexplorer.ui.feature.home.CircuitProvider
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.createGraphFactory

class NeoExplorerApplication : Application(), CircuitProvider {

    lateinit var applicationGraph: ApplicationGraph
        private set

    override val circuit: Circuit
        get() = applicationGraph.circuit

    override fun onCreate() {
        super.onCreate()
        applicationGraph = createGraphFactory<ApplicationGraph.Factory>()
            .create(this)
            .apply { inject(this@NeoExplorerApplication) }
    }
}
