package com.francesc.neoexplorer.data.neo.impl.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeoFeedResponse(
    @SerialName("element_count") val elementCount: Int,
    @SerialName("near_earth_objects") val nearEarthObjects: Map<String, List<NearEarthObjectDto>>,
)
