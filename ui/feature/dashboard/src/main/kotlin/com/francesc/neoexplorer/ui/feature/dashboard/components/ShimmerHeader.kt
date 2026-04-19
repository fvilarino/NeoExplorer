package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francesc.neoexplorer.ui.shared.compose.CardCornerSize
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.ShimmerHeightBody
import com.francesc.neoexplorer.ui.shared.compose.ShimmerHeightTitle
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@Composable
internal fun ShimmerHeader(modifier: Modifier = Modifier) {
    val shimmerBrush = rememberShimmerBrush()
    Column(modifier = modifier) {
        // Title line
        Box(
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .height(ShimmerHeightTitle)
                .background(shimmerBrush, RoundedCornerShape(CardCornerSize)),
        )
        Spacer(modifier = Modifier.height(MarginSingle))
        // Subtitle line
        Box(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(ShimmerHeightBody)
                .background(shimmerBrush, RoundedCornerShape(CardCornerSize)),
        )
    }
}

@WidgetPreviews
@Composable
private fun ShimmerHeaderPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ShimmerHeader(modifier = Modifier
                .fillMaxWidth()
                .padding(MarginDouble))
        }
    }
}
