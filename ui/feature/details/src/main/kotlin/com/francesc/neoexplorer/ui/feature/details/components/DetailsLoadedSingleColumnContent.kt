package com.francesc.neoexplorer.ui.feature.details.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.francesc.neoexplorer.ui.feature.details.R
import com.francesc.neoexplorer.ui.shared.compose.MarginOneAndHalf
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.francesc.neoexplorer.ui.shared.compose.plus

internal const val DetailsSingleColumnContainerTestTag = "details_single_column_container"

/** Max width of the size-comparison canvas on single-column layouts. */
private val CanvasMaxWidth = 600.dp

@Composable
internal fun DetailsLoadedSingleColumnContent(
  detailsUiModel: DetailsUiModel,
  horizontalPadding: androidx.compose.ui.unit.Dp,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
) {
  val context = LocalContext.current
  LazyColumn(
    modifier = modifier.fillMaxSize().testTag(DetailsSingleColumnContainerTestTag),
    contentPadding =
      PaddingValues(
        horizontal = horizontalPadding,
        vertical = MarginOneAndHalf,
      ) + contentPadding,
    verticalArrangement = Arrangement.spacedBy(MarginOneAndHalf),
  ) {
    // ── Hazardous badge ───────────────────────────────────────────────
    if (detailsUiModel.isPotentiallyHazardous) {
      item {
        HazardousWarningBanner(modifier = Modifier.fillMaxWidth())
      }
    }

    // ── Metrics grid ──────────────────────────────────────────────────
    item {
      SectionTitle(text = stringResource(R.string.section_object_characteristics))
    }
    item {
      MetricsGrid(asteroid = detailsUiModel)
    }

    // ── Size comparison ───────────────────────────────────────────────
    item {
      Spacer(modifier = Modifier.height(MarginSingle))
      HorizontalDivider()
      Spacer(modifier = Modifier.height(MarginSingle))
    }
    item {
      // Cap bar width so it doesn't become absurdly wide on Medium screens
      SizeComparisonCanvas(
        asteroidName = detailsUiModel.name,
        asteroidDiameterKm = detailsUiModel.diameterMaxKm,
        reference = detailsUiModel.sizeReference,
        modifier = Modifier.widthIn(max = CanvasMaxWidth).fillMaxWidth(),
      )
    }

    // ── JPL link button ───────────────────────────────────────────────
    item {
      Spacer(modifier = Modifier.height(MarginSingle))
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
