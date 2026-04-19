package com.francesc.neoexplorer

import android.app.Application
import dev.zacsweers.metro.createGraphFactory

class NeoExplorerApplication : Application() {

    lateinit var applicationGraph: ApplicationGraph
        private set

    override fun onCreate() {
        super.onCreate()
        applicationGraph = createGraphFactory<ApplicationGraph.Factory>()
            .create(this)
            .apply { inject(this@NeoExplorerApplication) }
    }
}
