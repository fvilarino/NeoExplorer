package com.francesc.neoexplorer.ui.feature.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.dashboard.components.AsteroidFeed
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardEvent
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.francesc.neoexplorer.ui.feature.dashboard.components.ErrorContent
import com.francesc.neoexplorer.ui.feature.dashboard.components.LoadingContent
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope
import kotlinx.datetime.LocalDate

@CircuitInject(DashboardScreen::class, AppScope::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardUi(state: DashboardUiState, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.foundation.layout.Column {
                        Text(
                            text = formatDate(state.date),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        if (state.loadingState == LoadingState.LOADED) {
                            Text(
                                text = "${state.hazardousCount} Potentially Hazardous Objects Today",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (state.hazardousCount > 0) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        when (state.loadingState) {
            LoadingState.LOADING -> LoadingContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
            LoadingState.LOADED -> AsteroidFeed(
                asteroids = state.asteroids,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
            LoadingState.ERROR -> ErrorContent(
                message = state.errorMessage ?: stringResource(R.string.something_went_wrong),
                onRetry = { state.eventSink(DashboardEvent.Retry) },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        }
    }
}

private fun formatDate(date: LocalDate): String {
    val monthName = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
    return "${date.dayOfMonth} $monthName ${date.year}"
}

