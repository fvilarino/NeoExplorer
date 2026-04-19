package com.francesc.neoexplorer.data.neo.impl

import dev.zacsweers.metro.Qualifier

/** Qualifier for the [retrofit2.Retrofit] instance configured for the NASA NeoWs base URL. */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NeoWsRetrofit
