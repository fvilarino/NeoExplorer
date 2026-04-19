package com.francesc.neoexplorer.core.clock

import kotlinx.datetime.LocalDate

interface DateProvider {
    fun today(): LocalDate
}
