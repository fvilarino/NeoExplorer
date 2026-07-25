package com.francesc.neoexplorer.core.formatter

import kotlinx.datetime.LocalDate

/** Interface for formatting [LocalDate] instances into user-readable strings. */
interface DateFormatter {
  /**
   * Formats a [date] into a string representation.
   *
   * @param date The date to be formatted.
   * @return A localized string representation of the date.
   */
  fun format(date: LocalDate): String
}
