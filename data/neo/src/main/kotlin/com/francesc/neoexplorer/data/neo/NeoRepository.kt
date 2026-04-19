package com.francesc.neoexplorer.data.neo

import androidx.paging.PagingData
import com.francesc.neoexplorer.data.neo.model.AsteroidId
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.data.neo.model.NeoFeed
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface NeoRepository {
    /**
     * NEO-Feed: retrieves a list of Asteroids based on their closest approach date to Earth.
     *
     * @param startDate Starting date for asteroid search.
     * @param endDate Ending date for asteroid search. If omitted, defaults to 7 days after [startDate].
     * @return [Result.success] with the feed on success, [Result.failure] with the cause on error.
     */
    suspend fun getFeed(startDate: LocalDate, endDate: LocalDate? = null): Result<NeoFeed>

    /**
     * NEO-Lookup: looks up a specific Asteroid based on its NASA JPL small body (SPK-ID) ID.
     *
     * @param asteroidId The SPK-ID of the asteroid.
     * @return [Result.success] with the asteroid on success, [Result.failure] with the cause on error.
     */
    suspend fun lookupAsteroid(asteroidId: AsteroidId): Result<NearEarthObject>

    /**
     * NEO-Browse: browse the overall Asteroid data-set as a [PagingData] stream.
     * Uses the Paging 3 library; collect the flow and submit to a [androidx.paging.PagingDataAdapter].
     */
    fun browse(): Flow<PagingData<NearEarthObject>>
}
