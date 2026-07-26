package com.francesc.neoexplorer.data.preferences.impl

import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import com.francesc.neoexplorer.data.preferences.AppPreferences
import com.francesc.neoexplorer.data.preferences.AppPreferencesRepository
import com.francesc.neoexplorer.data.preferences.AppTheme
import com.francesc.neoexplorer.data.preferences.impl.proto.AppPreferencesProto
import com.francesc.neoexplorer.data.preferences.impl.proto.AppThemeProto
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class AppPreferencesRepositoryImpl(private val dataStore: DataStore<AppPreferencesProto>) :
  AppPreferencesRepository {

  override val preferences: Flow<AppPreferences> = dataStore.data.map { it.toDomain() }

  override suspend fun setTheme(theme: AppTheme) {
    dataStore.updateData { current ->
      current.toBuilder().setTheme(theme.toProto()).build()
    }
  }

  override suspend fun setUseDynamicTheme(useDynamicTheme: Boolean) {
    dataStore.updateData { current ->
      current.toBuilder().setUseDynamicTheme(useDynamicTheme).build()
    }
  }
}

// ── Mappers ──────────────────────────────────────────────────────────────────

@VisibleForTesting
internal fun AppPreferencesProto.toDomain(): AppPreferences =
  AppPreferences(
    theme = theme.toDomain(),
    useDynamicTheme = useDynamicTheme,
  )

@VisibleForTesting
internal fun AppThemeProto.toDomain(): AppTheme =
  when (this) {
    AppThemeProto.APP_THEME_PROTO_LIGHT -> AppTheme.LIGHT
    AppThemeProto.APP_THEME_PROTO_DARK -> AppTheme.DARK
    // APP_THEME_PROTO_AUTO, UNRECOGNIZED, or any future value → safe default
    else -> AppTheme.AUTO
  }

@VisibleForTesting
internal fun AppTheme.toProto(): AppThemeProto =
  when (this) {
    AppTheme.AUTO -> AppThemeProto.APP_THEME_PROTO_AUTO
    AppTheme.LIGHT -> AppThemeProto.APP_THEME_PROTO_LIGHT
    AppTheme.DARK -> AppThemeProto.APP_THEME_PROTO_DARK
  }
