package com.francesc.neoexplorer.data.neo.impl.mapper

import com.francesc.neoexplorer.data.neo.impl.dto.CloseApproachDataDto
import com.francesc.neoexplorer.data.neo.impl.dto.NearEarthObjectDto
import com.francesc.neoexplorer.data.neo.impl.dto.NeoFeedResponse
import com.francesc.neoexplorer.data.neo.model.AsteroidId
import com.francesc.neoexplorer.data.neo.model.CloseApproachData
import com.francesc.neoexplorer.data.neo.model.EstimatedDiameter
import com.francesc.neoexplorer.data.neo.model.Kilometers
import com.francesc.neoexplorer.data.neo.model.KilometersPerSecond
import com.francesc.neoexplorer.data.neo.model.LunarDistances
import com.francesc.neoexplorer.data.neo.model.NasaJplUrl
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.data.neo.model.NeoFeed
import kotlinx.datetime.LocalDate

internal fun NearEarthObjectDto.toDomain(): NearEarthObject = NearEarthObject(
    id = AsteroidId(id),
    neoReferenceId = AsteroidId(neoReferenceId),
    name = name,
    absoluteMagnitudeH = absoluteMagnitudeH,
    estimatedDiameter = EstimatedDiameter(
        minKm = Kilometers(estimatedDiameter.kilometers.estimatedDiameterMin),
        maxKm = Kilometers(estimatedDiameter.kilometers.estimatedDiameterMax),
    ),
    isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid,
    closeApproachData = closeApproachData.map { it.toDomain() },
    nasaJplUrl = NasaJplUrl(nasaJplUrl),
)

internal fun CloseApproachDataDto.toDomain(): CloseApproachData = CloseApproachData(
    closeApproachDate = LocalDate.parse(closeApproachDate),
    relativeVelocityKmPerSecond = KilometersPerSecond(relativeVelocity.kilometersPerSecond.toDouble()),
    missDistanceKm = Kilometers(missDistance.kilometers.toDouble()),
    missDistanceLunar = LunarDistances(missDistance.lunar.toDouble()),
    orbitingBody = orbitingBody,
)

internal fun NeoFeedResponse.toDomain(): NeoFeed = NeoFeed(
    elementCount = elementCount,
    nearEarthObjects = nearEarthObjects
        .mapKeys { (dateStr, _) -> LocalDate.parse(dateStr) }
        .mapValues { (_, neos) -> neos.map { it.toDomain() } },
)
