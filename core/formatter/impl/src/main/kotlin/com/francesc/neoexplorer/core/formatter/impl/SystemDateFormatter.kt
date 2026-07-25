package com.francesc.neoexplorer.core.formatter.impl

import com.francesc.neoexplorer.core.formatter.DateFormatter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

@Inject
@ContributesBinding(AppScope::class)
class SystemDateFormatter : DateFormatter {
  override fun format(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.getDefault())
    return date.toJavaLocalDate().format(formatter)
  }
}
