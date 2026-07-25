package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.asteroid.AsteroidFeedLoadingContent

@Composable
fun LoadingContent(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
) {
  AsteroidFeedLoadingContent(modifier = modifier, contentPadding = contentPadding)
}
