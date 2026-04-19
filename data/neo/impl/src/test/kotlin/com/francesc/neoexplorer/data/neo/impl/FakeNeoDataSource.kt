package com.francesc.neoexplorer.data.neo.impl
import com.francesc.neoexplorer.data.neo.impl.dto.NearEarthObjectDto
import com.francesc.neoexplorer.data.neo.impl.dto.NeoBrowseResponse
import com.francesc.neoexplorer.data.neo.impl.dto.NeoFeedResponse
internal class FakeNeoDataSource : NeoDataSource {
    // --- Configurable responses ---
    var feedResponse: NeoFeedResponse = neoFeedResponse()
    var lookupResponse: NearEarthObjectDto = nearEarthObjectDto()
    var browseResponse: NeoBrowseResponse = neoBrowseResponse()
    /** When set, every call will throw this instead of returning a response. */
    var error: Throwable? = null
    // --- Recorded call arguments ---
    var lastFeedStartDate: String? = null
        private set
    var lastFeedEndDate: String? = null
        private set
    var lastLookupAsteroidId: String? = null
        private set
    var lastBrowsePage: Int? = null
        private set
    var lastBrowsePageSize: Int? = null
        private set
    override suspend fun getFeed(startDate: String, endDate: String?): NeoFeedResponse {
        error?.let { throw it }
        lastFeedStartDate = startDate
        lastFeedEndDate = endDate
        return feedResponse
    }
    override suspend fun lookupAsteroid(asteroidId: String): NearEarthObjectDto {
        error?.let { throw it }
        lastLookupAsteroidId = asteroidId
        return lookupResponse
    }
    override suspend fun browse(page: Int, pageSize: Int): NeoBrowseResponse {
        error?.let { throw it }
        lastBrowsePage = page
        lastBrowsePageSize = pageSize
        return browseResponse
    }
}
