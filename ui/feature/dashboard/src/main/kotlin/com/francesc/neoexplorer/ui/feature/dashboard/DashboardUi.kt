package com.francesc.neoexplorer.ui.feature.dashboard

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.dashboard.components.AsteroidFeed
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardEvent
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardScreen
import com.francesc.neoexplorer.ui.feature.dashboard.components.DashboardTopBar
import com.francesc.neoexplorer.ui.feature.dashboard.components.ErrorContent
import com.francesc.neoexplorer.ui.feature.dashboard.components.LoadingContent
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope

@CircuitInject(DashboardScreen::class, AppScope::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardUi(state: DashboardUiState, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DashboardTopBar(
                isLoaded = state.loadingState == LoadingState.LOADED,
                sortOrder = state.sortOrder,
                onSortOrderChange = { state.eventSink(DashboardEvent.SetSortOrder(it)) },
            )
        },
    ) { innerPadding ->
        Crossfade(targetState = state.loadingState, label = "dashboardState") { loadingState ->
            when (loadingState) {
                LoadingState.LOADING -> LoadingContent(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding,
                )
                LoadingState.LOADED -> AsteroidFeed(
                    asteroids = state.asteroids,
                    date = state.date,
                    hazardousCount = state.hazardousCount,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding,
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
}
