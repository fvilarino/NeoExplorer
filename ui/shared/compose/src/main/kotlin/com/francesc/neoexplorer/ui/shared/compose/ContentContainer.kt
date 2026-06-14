package com.francesc.neoexplorer.ui.shared.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Max dp width for single-column detail content on wide screens. */
val MaxContentWidth = 840.dp

/**
 * A centering container that caps its inner content at [MaxContentWidth] and centres it
 * horizontally within the available space.  Use this to prevent single-column screens from
 * becoming uncomfortably wide on tablets in medium window-width mode.
 *
 * Example usage — wrap the content area of a Scaffold:
 * ```
 * ContentContainer(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
 *     LazyColumn(modifier = Modifier.fillMaxSize()) { … }
 * }
 * ```
 */
@Composable
fun ContentContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
        Box(
            modifier = Modifier
                .widthIn(max = MaxContentWidth)
                .fillMaxSize(),
            content = content,
        )
    }
}
