package com.francesc.neoexplorer

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.annotation.Keep
import androidx.core.app.AppComponentFactory
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@Keep
class NeoExplorerAppComponentFactory : AppComponentFactory() {

  private inline fun <reified T : Any> getInstance(
    cl: ClassLoader,
    className: String,
    providers: Map<KClass<out T>, Provider<T>>,
  ): T? {
    val clazz = Class.forName(className, false, cl).asSubclass(T::class.java)
    return providers[clazz.kotlin]?.invoke()
  }

  override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
    val app = super.instantiateApplicationCompat(cl, className)
    applicationRef = app as NeoExplorerApplication
    return app
  }

  override fun instantiateActivityCompat(
    cl: ClassLoader,
    className: String,
    intent: Intent?,
  ): Activity =
    getInstance(cl, className, applicationRef.applicationGraph.activityProviders)
      ?: super.instantiateActivityCompat(cl, className, intent)

  companion object {
    lateinit var applicationRef: NeoExplorerApplication
      private set
  }
}
