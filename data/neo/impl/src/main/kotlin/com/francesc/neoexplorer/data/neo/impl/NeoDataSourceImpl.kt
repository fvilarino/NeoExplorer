package com.francesc.neoexplorer.data.neo.impl

import com.francesc.neoexplorer.data.neo.impl.dto.NearEarthObjectDto
import com.francesc.neoexplorer.data.neo.impl.dto.NeoBrowseResponse
import com.francesc.neoexplorer.data.neo.impl.dto.NeoFeedResponse
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.SingleIn
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private interface NeoWsService {
    @GET("feed")
    suspend fun getFeed(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String?,
        @Query("api_key") apiKey: String,
    ): NeoFeedResponse

    @GET("neo/{asteroid_id}")
    suspend fun lookupAsteroid(
        @Path("asteroid_id") asteroidId: String,
        @Query("api_key") apiKey: String,
    ): NearEarthObjectDto

    @GET("neo/browse")
    suspend fun browse(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("api_key") apiKey: String,
    ): NeoBrowseResponse
}

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NeoDataSourceImpl(
    @param:NeoWsRetrofit private val neoWsRetrofit: retrofit2.Retrofit,
    @param:Named("nasa_api_key") private val apiKey: String,
) : NeoDataSource {
    private val service: NeoWsService = neoWsRetrofit.create(NeoWsService::class.java)

    override suspend fun getFeed(startDate: String, endDate: String?): NeoFeedResponse =
        service.getFeed(startDate, endDate, apiKey)

    override suspend fun lookupAsteroid(asteroidId: String): NearEarthObjectDto =
        service.lookupAsteroid(asteroidId, apiKey)

    override suspend fun browse(page: Int, pageSize: Int): NeoBrowseResponse =
        service.browse(page, pageSize, apiKey)
}
