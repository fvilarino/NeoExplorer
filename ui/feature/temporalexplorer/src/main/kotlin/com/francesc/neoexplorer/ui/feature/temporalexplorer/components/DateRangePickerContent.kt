package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.temporalexplorer.R
import com.francesc.neoexplorer.ui.shared.compose.MarginDouble
import com.francesc.neoexplorer.ui.shared.compose.MarginSingle
import com.slack.circuit.overlay.OverlayNavigator
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

private const val MAX_RANGE_DAYS = 7L
private const val MS_PER_DAY = 24L * 60 * 60 * 1_000

private fun LocalDate.toUtcMillis(): Long = atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()

private fun Long.toLocalDateUtc(): LocalDate {
  val instant = kotlin.time.Instant.fromEpochMilliseconds(this)
  return instant.toLocalDateTime(TimeZone.UTC).date
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerContent(
  navigator: OverlayNavigator<DateRangeResult>,
  initialStartDate: LocalDate?,
  initialEndDate: LocalDate?,
) {
  val pickerState =
    rememberDateRangePickerState(
      initialSelectedStartDateMillis = initialStartDate?.toUtcMillis(),
      initialSelectedEndDateMillis = initialEndDate?.toUtcMillis(),
    )

  val rangeExceedsMax by remember {
    derivedStateOf {
      val start = pickerState.selectedStartDateMillis
      val end = pickerState.selectedEndDateMillis
      if (start != null && end != null) {
        end - start > (MAX_RANGE_DAYS - 1) * MS_PER_DAY
      } else {
        false
      }
    }
  }

  val confirmEnabled by remember {
    derivedStateOf {
      pickerState.selectedStartDateMillis != null &&
        pickerState.selectedEndDateMillis != null &&
        !rangeExceedsMax
    }
  }

  Column(modifier = Modifier.fillMaxWidth().padding(bottom = MarginDouble)) {
    DateRangePicker(
      state = pickerState,
      modifier = Modifier.weight(1f, fill = false),
    )

    if (rangeExceedsMax) {
      Text(
        text = stringResource(R.string.temporal_explorer_range_too_long),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(all = MarginDouble),
      )
      Spacer(modifier = Modifier.height(MarginSingle))
    }

    Row(
      modifier = Modifier.fillMaxWidth().padding(horizontal = MarginDouble),
      horizontalArrangement = Arrangement.End,
    ) {
      TextButton(onClick = { navigator.finish(DateRangeResult.Dismissed) }) {
        Text(stringResource(R.string.temporal_explorer_cancel))
      }
      TextButton(
        onClick = {
          val startMs = pickerState.selectedStartDateMillis ?: return@TextButton
          val endMs = pickerState.selectedEndDateMillis ?: return@TextButton
          navigator.finish(
            DateRangeResult.Selected(
              startDate = startMs.toLocalDateUtc(),
              endDate = endMs.toLocalDateUtc(),
            )
          )
        },
        enabled = confirmEnabled,
      ) {
        Text(stringResource(R.string.temporal_explorer_search))
      }
    }
  }
}
