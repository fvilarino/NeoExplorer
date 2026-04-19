package com.francesc.neoexplorer.data.neo.impl

import com.francesc.neoexplorer.data.neo.impl.dto.NearEarthObjectDto
import com.francesc.neoexplorer.data.neo.impl.dto.NeoBrowseResponse
import com.francesc.neoexplorer.data.neo.impl.dto.NeoFeedResponse

interface NeoDataSource {
    /** Fetches the NEO feed for the given date range. */
    suspend fun getFeed(startDate: String, endDate: String?): NeoFeedResponse

    /** Fetches a single NEO by its SPK-ID. */
    suspend fun lookupAsteroid(asteroidId: String): NearEarthObjectDto

    /** Fetches a single page of the NEO browse dataset. */
    suspend fun browse(page: Int, pageSize: Int): NeoBrowseResponse
}
