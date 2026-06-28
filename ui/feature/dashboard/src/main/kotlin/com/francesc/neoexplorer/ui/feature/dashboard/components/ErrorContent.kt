package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.asteroid.AsteroidFeedErrorContent

@Composable
fun ErrorContent(
  message: String,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier,
) {
  AsteroidFeedErrorContent(message = message, onRetry = onRetry, modifier = modifier)
}
