package com.francesc.neoexplorer.data.neo.impl.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeoBrowsePageInfo(
    @SerialName("size") val size: Int,
    @SerialName("total_elements") val totalElements: Long,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("number") val number: Int,
)

@Serializable
data class NeoBrowseResponse(
    @SerialName("near_earth_objects") val nearEarthObjects: List<NearEarthObjectDto>,
    @SerialName("page") val page: NeoBrowsePageInfo,
)
