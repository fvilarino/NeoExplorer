package com.francesc.neoexplorer.data.neo.impl

import androidx.paging.testing.asSnapshot
import com.francesc.neoexplorer.data.neo.model.AsteroidId
import com.francesc.neoexplorer.data.neo.model.Kilometers
import com.francesc.neoexplorer.data.neo.model.KilometersPerSecond
import com.francesc.neoexplorer.data.neo.model.NasaJplUrl
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NeoRepositoryImplTest {

    private val dataSource = FakeNeoDataSource()
    private val repository = NeoRepositoryImpl(dataSource)

    // region getFeed
    @Test
    fun `getFeed passes formatted start date to data source`() = runTest {
        repository.getFeed(startDate = LocalDate(2025, 1, 15))
        assertEquals("2025-01-15", dataSource.lastFeedStartDate)
    }

    @Test
    fun `getFeed passes formatted end date to data source`() = runTest {
        repository.getFeed(
            startDate = LocalDate(2025, 1, 15),
            endDate = LocalDate(2025, 1, 22),
        )
        assertEquals("2025-01-22", dataSource.lastFeedEndDate)
    }

    @Test
    fun `getFeed passes null end date to data source when not provided`() = runTest {
        repository.getFeed(startDate = LocalDate(2025, 1, 15))
        assertNull(dataSource.lastFeedEndDate)
    }

    @Test
    fun `getFeed maps element count from response`() = runTest {
        dataSource.feedResponse = neoFeedResponse(elementCount = 7)
        val result = repository.getFeed(startDate = LocalDate(2025, 1, 15)).getOrThrow()
        assertEquals(7, result.elementCount)
    }

    @Test
    fun `getFeed maps near earth objects keyed by LocalDate`() = runTest {
        dataSource.feedResponse = neoFeedResponse(
            nearEarthObjects = mapOf(
                "2025-01-15" to listOf(nearEarthObjectDto(id = "111")),
                "2025-01-16" to listOf(nearEarthObjectDto(id = "222")),
            ),
        )
        val result = repository.getFeed(startDate = LocalDate(2025, 1, 15)).getOrThrow()
        assertEquals(2, result.nearEarthObjects.size)
        assertTrue(result.nearEarthObjects.containsKey(LocalDate(2025, 1, 15)))
        assertTrue(result.nearEarthObjects.containsKey(LocalDate(2025, 1, 16)))
    }

    @Test
    fun `getFeed maps near earth object fields correctly`() = runTest {
        dataSource.feedResponse = neoFeedResponse(
            nearEarthObjects = mapOf(
                "2025-01-15" to listOf(
                    nearEarthObjectDto(
                        id = "99999",
                        name = "Test Asteroid",
                        absoluteMagnitudeH = 21.3,
                        diameterMinKm = 0.05,
                        diameterMaxKm = 0.11,
                        isPotentiallyHazardous = true,
                        nasaJplUrl = "https://ssd.jpl.nasa.gov/tools/sbdb_lookup.html#/?sstr=99999",
                    ),
                ),
            ),
        )
        val neo = repository.getFeed(startDate = LocalDate(2025, 1, 15))
            .getOrThrow()
            .nearEarthObjects[LocalDate(2025, 1, 15)]!!
            .single()
        assertEquals(AsteroidId("99999"), neo.id)
        assertEquals("Test Asteroid", neo.name)
        assertEquals(21.3, neo.absoluteMagnitudeH, 0.0)
        assertEquals(Kilometers(0.05), neo.estimatedDiameter.minKm)
        assertEquals(Kilometers(0.11), neo.estimatedDiameter.maxKm)
        assertTrue(neo.isPotentiallyHazardousAsteroid)
        assertEquals(
            NasaJplUrl("https://ssd.jpl.nasa.gov/tools/sbdb_lookup.html#/?sstr=99999"),
            neo.nasaJplUrl
        )
    }

    @Test
    fun `getFeed maps close approach data correctly`() = runTest {
        dataSource.feedResponse = neoFeedResponse(
            nearEarthObjects = mapOf(
                "2025-01-15" to listOf(
                    nearEarthObjectDto(
                        closeApproachData = listOf(
                            closeApproachDataDto(
                                closeApproachDate = "2025-03-20",
                                velocityKmPerSecond = "8.75",
                                missDistanceKm = "750000.5",
                                orbitingBody = "Mars",
                            ),
                        ),
                    ),
                ),
            ),
        )
        val approach = repository.getFeed(startDate = LocalDate(2025, 1, 15))
            .getOrThrow()
            .nearEarthObjects[LocalDate(2025, 1, 15)]!!
            .single()
            .closeApproachData
            .single()
        assertEquals(LocalDate(2025, 3, 20), approach.closeApproachDate)
        assertEquals(KilometersPerSecond(8.75), approach.relativeVelocityKmPerSecond)
        assertEquals(Kilometers(750000.5), approach.missDistanceKm)
        assertEquals("Mars", approach.orbitingBody)
    }

    @Test
    fun `getFeed returns failure when data source throws`() = runTest {
        val exception = RuntimeException("network error")
        dataSource.error = exception
        val result = repository.getFeed(startDate = LocalDate(2025, 1, 15))
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getFeed rethrows CancellationException`() = runTest {
        dataSource.error = CancellationException("cancelled")

        try {
            repository.getFeed(startDate = LocalDate(2025, 1, 15))
            error("Expected CancellationException to be thrown")
        } catch (e: CancellationException) {
            assertEquals("cancelled", e.message)
        }
    }

    // endregion
    // region lookupAsteroid
    @Test
    fun `lookupAsteroid passes asteroid id value to data source`() = runTest {
        repository.lookupAsteroid(AsteroidId("3542519"))
        assertEquals("3542519", dataSource.lastLookupAsteroidId)
    }

    @Test
    fun `lookupAsteroid maps response fields correctly`() = runTest {
        dataSource.lookupResponse = nearEarthObjectDto(
            id = "3542519",
            neoReferenceId = "3542519",
            name = "433 Eros",
            absoluteMagnitudeH = 10.4,
            diameterMinKm = 8.0,
            diameterMaxKm = 20.0,
            isPotentiallyHazardous = false,
        )
        val result = repository.lookupAsteroid(AsteroidId("3542519")).getOrThrow()
        assertEquals(AsteroidId("3542519"), result.id)
        assertEquals(AsteroidId("3542519"), result.neoReferenceId)
        assertEquals("433 Eros", result.name)
        assertEquals(10.4, result.absoluteMagnitudeH, 0.0)
        assertEquals(Kilometers(8.0), result.estimatedDiameter.minKm)
        assertEquals(Kilometers(20.0), result.estimatedDiameter.maxKm)
        assertEquals(false, result.isPotentiallyHazardousAsteroid)
    }

    @Test
    fun `lookupAsteroid returns failure when data source throws`() = runTest {
        val exception = RuntimeException("not found")
        dataSource.error = exception
        val result = repository.lookupAsteroid(AsteroidId("0"))
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `lookupAsteroid rethrows CancellationException`() = runTest {
        dataSource.error = CancellationException("cancelled")

        try {
            repository.lookupAsteroid(AsteroidId("0"))
            error("Expected CancellationException to be thrown")
        } catch (e: CancellationException) {
            assertEquals("cancelled", e.message)
        }
    }

    // endregion
    // region browse
    @Test
    fun `browse emits paged near earth objects`() = runTest {
        val neos = (1..5).map { nearEarthObjectDto(id = "$it", name = "NEO $it") }
        dataSource.browseResponse = neoBrowseResponse(neos = neos, totalPages = 1)
        val result = repository.browse().asSnapshot()
        assertEquals(5, result.size)
        assertEquals(AsteroidId("1"), result.first().id)
        assertEquals(AsteroidId("5"), result.last().id)
    }

    @Test
    fun `browse fetches multiple pages`() = runTest {
        val page0Neos = (1..3).map { nearEarthObjectDto(id = "$it") }
        val page1Neos = (4..5).map { nearEarthObjectDto(id = "$it") }
        var pageRequested = 0
        val multiPageDataSource = object : NeoDataSource by dataSource {
            override suspend fun browse(page: Int, pageSize: Int) = when (page) {
                0 -> neoBrowseResponse(neos = page0Neos, page = 0, totalPages = 2)
                else -> neoBrowseResponse(neos = page1Neos, page = 1, totalPages = 2)
            }.also { pageRequested = page }
        }
        val multiPageRepository = NeoRepositoryImpl(multiPageDataSource)
        val result = multiPageRepository.browse().asSnapshot {
            scrollTo(index = 4)
        }
        assertEquals(5, result.size)
        assertEquals(1, pageRequested)
    }
    // endregion
}
