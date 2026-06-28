package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.slack.circuit.overlay.Overlay
import com.slack.circuit.overlay.OverlayNavigator
import kotlinx.datetime.LocalDate

/** Circuit [Overlay] that presents a date range picker inside a [ModalBottomSheet]. */
@OptIn(ExperimentalMaterial3Api::class)
class DateRangeOverlay(
  private val initialStartDate: LocalDate?,
  private val initialEndDate: LocalDate?,
) : Overlay<DateRangeResult> {

  @Composable
  override fun Content(navigator: OverlayNavigator<DateRangeResult>) {
    ModalBottomSheet(onDismissRequest = { navigator.finish(DateRangeResult.Dismissed) }) {
      DateRangePickerContent(
        navigator = navigator,
        initialStartDate = initialStartDate,
        initialEndDate = initialEndDate,
      )
    }
  }
}
