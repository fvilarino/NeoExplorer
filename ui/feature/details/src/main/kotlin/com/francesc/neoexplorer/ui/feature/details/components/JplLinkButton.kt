package com.francesc.neoexplorer.ui.feature.details.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.compose.IconSizeSmall
import com.francesc.neoexplorer.ui.shared.compose.MarginHalf
import com.francesc.neoexplorer.ui.shared.compose.PhonePreviews
import com.francesc.neoexplorer.ui.shared.compose.TabletPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

/** Max width of the JPL link button so it never becomes a full-tablet-width banner. */
internal val JplButtonMaxWidth = 320.dp

@Composable
internal fun JplLinkButton(
    onOpen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onOpen,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        Icon(
            imageVector = Icons.Filled.OpenInBrowser,
            contentDescription = null,
            modifier = Modifier.size(IconSizeSmall),
        )
        Spacer(modifier = Modifier.width(MarginHalf))
        Text(text = stringResource(R.string.open_jpl_button))
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@PhonePreviews
@TabletPreviews
@Composable
private fun JplLinkButtonPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            JplLinkButton(
                onOpen = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
