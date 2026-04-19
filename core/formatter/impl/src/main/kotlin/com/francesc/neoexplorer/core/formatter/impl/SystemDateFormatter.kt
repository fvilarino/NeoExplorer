package com.francesc.neoexplorer.core.formatter.impl

import com.francesc.neoexplorer.core.formatter.DateFormatter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.datetime.LocalDate

@Inject
@ContributesBinding(AppScope::class)
class SystemDateFormatter : DateFormatter {
    override fun format(date: LocalDate): String {
        val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
        return "${date.dayOfMonth} $month ${date.year}"
    }
}
