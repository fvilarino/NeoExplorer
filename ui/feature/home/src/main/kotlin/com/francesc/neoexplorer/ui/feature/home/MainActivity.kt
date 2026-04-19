package com.francesc.neoexplorer.ui.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val circuit = (application as CircuitProvider).circuit
        setContent {
            NeoExplorerTheme {
                HomeScreen(
                    circuit = circuit,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
