package com.francesc.neoexplorer.data.preferences.impl

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.francesc.neoexplorer.core.dispather.DispatcherProvider
import com.francesc.neoexplorer.data.preferences.impl.proto.AppPreferencesProto
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

private const val APP_PREFERENCES_FILE = "app_preferences.pb"

@ContributesTo(AppScope::class)
interface AppPreferencesModule {
    companion object {
        /**
         * Provides a process-lifetime [DataStore] for [AppPreferencesProto].
         *
         * The backing file is stored in the app's private files directory and outlives any
         * individual Activity or ViewModel. A dedicated [SupervisorJob] is used so that DataStore
         * failures don't cancel the rest of the app's coroutine hierarchy.
         */
        @Provides
        @SingleIn(AppScope::class)
        fun provideAppPreferencesDataStore(
            application: Application,
            dispatcherProvider: DispatcherProvider,
        ): DataStore<AppPreferencesProto> = DataStoreFactory.create(
            serializer = AppPreferencesSerializer,
            produceFile = { application.dataStoreFile(APP_PREFERENCES_FILE) },
            scope = CoroutineScope(dispatcherProvider.io + SupervisorJob()),
        )
    }
}
