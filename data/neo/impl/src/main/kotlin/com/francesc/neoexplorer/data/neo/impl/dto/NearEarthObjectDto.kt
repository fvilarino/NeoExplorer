package com.francesc.neoexplorer.data.neo.impl.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NearEarthObjectDto(
    @SerialName("id") val id: String,
    @SerialName("neo_reference_id") val neoReferenceId: String,
    @SerialName("name") val name: String,
    @SerialName("absolute_magnitude_h") val absoluteMagnitudeH: Double,
    @SerialName("estimated_diameter") val estimatedDiameter: EstimatedDiameterDto,
    @SerialName("is_potentially_hazardous_asteroid") val isPotentiallyHazardousAsteroid: Boolean,
    @SerialName("close_approach_data") val closeApproachData: List<CloseApproachDataDto>,
    @SerialName("nasa_jpl_url") val nasaJplUrl: String,
)
