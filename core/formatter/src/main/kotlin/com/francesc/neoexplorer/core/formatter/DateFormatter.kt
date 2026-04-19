package com.francesc.neoexplorer.core.formatter

import kotlinx.datetime.LocalDate

interface DateFormatter {
    fun format(date: LocalDate): String
}

