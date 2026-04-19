package com.francesc.neoexplorer.data.neo.impl.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelativeVelocityDto(
    @SerialName("kilometers_per_second") val kilometersPerSecond: String,
)

@Serializable
data class MissDistanceDto(
    @SerialName("kilometers") val kilometers: String,
)

@Serializable
data class CloseApproachDataDto(
    @SerialName("close_approach_date") val closeApproachDate: String,
    @SerialName("relative_velocity") val relativeVelocity: RelativeVelocityDto,
    @SerialName("miss_distance") val missDistance: MissDistanceDto,
    @SerialName("orbiting_body") val orbitingBody: String,
)
