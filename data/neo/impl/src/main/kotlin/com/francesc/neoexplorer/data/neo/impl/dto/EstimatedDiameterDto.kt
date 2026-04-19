package com.francesc.neoexplorer.data.neo.impl.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EstimatedDiameterRangeDto(
    @SerialName("estimated_diameter_min") val estimatedDiameterMin: Double,
    @SerialName("estimated_diameter_max") val estimatedDiameterMax: Double,
)

@Serializable
data class EstimatedDiameterDto(
    @SerialName("kilometers") val kilometers: EstimatedDiameterRangeDto,
)
