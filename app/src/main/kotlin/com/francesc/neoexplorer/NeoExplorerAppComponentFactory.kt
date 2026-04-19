package com.francesc.neoexplorer

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.annotation.Keep
import androidx.core.app.AppComponentFactory

@Keep
class NeoExplorerAppComponentFactory : AppComponentFactory() {

    override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
        val app = super.instantiateApplicationCompat(cl, className)
        applicationRef = app as NeoExplorerApplication
        return app
    }

    override fun instantiateActivityCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?,
    ): Activity = super.instantiateActivityCompat(cl, className, intent)

    companion object {
        lateinit var applicationRef: NeoExplorerApplication
            private set
    }
}
