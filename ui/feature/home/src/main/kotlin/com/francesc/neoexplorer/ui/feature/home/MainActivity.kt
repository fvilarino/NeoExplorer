package com.francesc.neoexplorer.ui.feature.home

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francesc.neoexplorer.data.preferences.AppPreferences
import com.francesc.neoexplorer.data.preferences.AppPreferencesRepository
import com.francesc.neoexplorer.data.preferences.AppTheme
import com.francesc.neoexplorer.ui.feature.home.di.ActivityKey
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding

@ActivityKey(MainActivity::class)
@ContributesIntoMap(AppScope::class, binding = binding<Activity>())
@Inject
class MainActivity(
  private val circuit: Circuit,
  private val appPreferencesRepository: AppPreferencesRepository,
) : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val prefs by
        appPreferencesRepository.preferences.collectAsStateWithLifecycle(
          AppPreferences(AppTheme.AUTO, false)
        )
      val darkTheme =
        when (prefs.theme) {
          AppTheme.LIGHT -> false
          AppTheme.DARK -> true
          AppTheme.AUTO -> isSystemInDarkTheme()
        }
      SideEffect {
        enableEdgeToEdge(
          statusBarStyle =
            if (darkTheme) {
              SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
            } else {
              SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
              )
            }
        )
      }
      NeoExplorerTheme(
        darkTheme = darkTheme,
        useDynamicTheme = prefs.useDynamicTheme,
      ) {
        HomeScreen(
          circuit = circuit,
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}
