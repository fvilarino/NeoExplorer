package com.francesc.neoexplorer.data.preferences

import kotlinx.coroutines.flow.Flow

/**
 * Source of truth for persisted app preferences.
 *
 * All write operations are `suspend` so callers can propagate back-pressure and errors without
 * blocking the main thread.
 */
interface AppPreferencesRepository {

  /**
   * A cold [Flow] that emits the current [AppPreferences] and then each subsequent update. Backed
   * by DataStore – it never completes normally and emits exactly one item on collection before
   * suspending until the next change.
   */
  val preferences: Flow<AppPreferences>

  /** Persists [theme] as the new app theme preference. */
  suspend fun setTheme(theme: AppTheme)

  /** Persists [useDynamicTheme] as the dynamic-colour preference flag. */
  suspend fun setUseDynamicTheme(useDynamicTheme: Boolean)
}
