package com.francesc.neoexplorer.ui.feature.details.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf

@Composable
internal fun DetailsLoadedTwoPaneContent(
  detailsUiModel: DetailsUiModel,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  Row(
    modifier = modifier.padding(horizontal = MarginDouble),
    horizontalArrangement = Arrangement.spacedBy(MarginDouble),
  ) {
    // ── Left pane: hazard banner + metrics ────────────────────────────
    LazyColumn(
      modifier = Modifier.weight(1f),
      contentPadding = PaddingValues(vertical = MarginOneAndHalf),
      verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
    ) {
      if (detailsUiModel.isPotentiallyHazardous) {
        item {
          HazardousWarningBanner(modifier = Modifier.fillMaxWidth())
        }
      }
      item {
        SectionTitle(text = stringResource(R.string.section_object_characteristics))
      }
      item {
        MetricsGrid(asteroid = detailsUiModel, columns = 3)
      }
    }

    VerticalDivider(modifier = Modifier.fillMaxHeight())

    // ── Right pane: size comparison + JPL button ──────────────────────
    LazyColumn(
      modifier = Modifier.weight(1f),
      contentPadding = PaddingValues(vertical = MarginOneAndHalf),
      verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
    ) {
      item {
        SizeComparisonCanvas(
          asteroidName = detailsUiModel.name,
          asteroidDiameterKm = detailsUiModel.diameterMaxKm,
          reference = detailsUiModel.sizeReference,
          modifier = Modifier.fillMaxWidth(),
        )
      }
      item {
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center,
        ) {
          JplLinkButton(
            onOpen = {
              context.startActivity(Intent(Intent.ACTION_VIEW, detailsUiModel.nasaJplUrl.toUri()))
            },
            modifier = Modifier.widthIn(max = JplButtonMaxWidth).fillMaxWidth(),
          )
        }
      }
    }
  }
}
