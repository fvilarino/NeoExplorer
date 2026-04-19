package com.francesc.neoexplorer.data.neo.impl
import com.francesc.neoexplorer.data.neo.impl.dto.CloseApproachDataDto
import com.francesc.neoexplorer.data.neo.impl.dto.EstimatedDiameterDto
import com.francesc.neoexplorer.data.neo.impl.dto.EstimatedDiameterRangeDto
import com.francesc.neoexplorer.data.neo.impl.dto.MissDistanceDto
import com.francesc.neoexplorer.data.neo.impl.dto.NearEarthObjectDto
import com.francesc.neoexplorer.data.neo.impl.dto.NeoBrowsePageInfo
import com.francesc.neoexplorer.data.neo.impl.dto.NeoBrowseResponse
import com.francesc.neoexplorer.data.neo.impl.dto.NeoFeedResponse
import com.francesc.neoexplorer.data.neo.impl.dto.RelativeVelocityDto
internal fun closeApproachDataDto(
    closeApproachDate: String = "2025-01-15",
    velocityKmPerSecond: String = "12.345",
    missDistanceKm: String = "500000.0",
    orbitingBody: String = "Earth",
) = CloseApproachDataDto(
    closeApproachDate = closeApproachDate,
    relativeVelocity = RelativeVelocityDto(kilometersPerSecond = velocityKmPerSecond),
    missDistance = MissDistanceDto(kilometers = missDistanceKm),
    orbitingBody = orbitingBody,
)
internal fun nearEarthObjectDto(
    id: String = "54321",
    neoReferenceId: String = "54321",
    name: String = "2023 XY1",
    absoluteMagnitudeH: Double = 18.5,
    diameterMinKm: Double = 0.1,
    diameterMaxKm: Double = 0.3,
    isPotentiallyHazardous: Boolean = false,
    closeApproachData: List<CloseApproachDataDto> = listOf(closeApproachDataDto()),
    nasaJplUrl: String = "https://ssd.jpl.nasa.gov/tools/sbdb_lookup.html#/?sstr=54321",
) = NearEarthObjectDto(
    id = id,
    neoReferenceId = neoReferenceId,
    name = name,
    absoluteMagnitudeH = absoluteMagnitudeH,
    estimatedDiameter = EstimatedDiameterDto(
        kilometers = EstimatedDiameterRangeDto(
            estimatedDiameterMin = diameterMinKm,
            estimatedDiameterMax = diameterMaxKm,
        ),
    ),
    isPotentiallyHazardousAsteroid = isPotentiallyHazardous,
    closeApproachData = closeApproachData,
    nasaJplUrl = nasaJplUrl,
)
internal fun neoFeedResponse(
    elementCount: Int = 1,
    nearEarthObjects: Map<String, List<NearEarthObjectDto>> = mapOf(
        "2025-01-15" to listOf(nearEarthObjectDto()),
    ),
) = NeoFeedResponse(
    elementCount = elementCount,
    nearEarthObjects = nearEarthObjects,
)
internal fun neoBrowseResponse(
    neos: List<NearEarthObjectDto> = listOf(nearEarthObjectDto()),
    page: Int = 0,
    totalPages: Int = 1,
    totalElements: Long = 1L,
    pageSize: Int = 20,
) = NeoBrowseResponse(
    nearEarthObjects = neos,
    page = NeoBrowsePageInfo(
        size = pageSize,
        totalElements = totalElements,
        totalPages = totalPages,
        number = page,
    ),
)
