package com.francesc.neoexplorer.ui.feature.temporalexplorer.components

import kotlinx.datetime.LocalDate

/** Non-nullable result type for the date range overlay. */
interface DateRangeResult {
  data class Selected(val startDate: LocalDate, val endDate: LocalDate) : DateRangeResult

  data object Dismissed : DateRangeResult
}
