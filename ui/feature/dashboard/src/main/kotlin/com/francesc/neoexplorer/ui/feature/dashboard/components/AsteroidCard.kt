package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AsteroidCard(
  asteroid: AsteroidUiModel,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidCard(
    asteroid = asteroid,
    onClick = onClick,
    modifier = modifier,
  )
}
