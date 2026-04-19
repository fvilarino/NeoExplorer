package com.francesc.neoexplorer.data.neo.model

import kotlinx.datetime.LocalDate

data class NeoFeed(
    val elementCount: Int,
    /** Keyed by date (yyyy-MM-dd). */
    val nearEarthObjects: Map<LocalDate, List<NearEarthObject>>,
)
