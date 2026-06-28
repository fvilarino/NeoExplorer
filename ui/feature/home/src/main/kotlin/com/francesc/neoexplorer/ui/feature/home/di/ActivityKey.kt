package com.francesc.neoexplorer.ui.feature.home.di

import android.app.Activity
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class ActivityKey(val value: KClass<out Activity>)
