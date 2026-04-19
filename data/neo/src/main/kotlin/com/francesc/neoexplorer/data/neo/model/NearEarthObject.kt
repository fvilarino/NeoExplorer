package com.francesc.neoexplorer.data.neo.model

data class NearEarthObject(
    val id: AsteroidId,
    val neoReferenceId: AsteroidId,
    val name: String,
    val absoluteMagnitudeH: Double,
    val estimatedDiameter: EstimatedDiameter,
    val isPotentiallyHazardousAsteroid: Boolean,
    val closeApproachData: List<CloseApproachData>,
    val nasaJplUrl: NasaJplUrl,
)
