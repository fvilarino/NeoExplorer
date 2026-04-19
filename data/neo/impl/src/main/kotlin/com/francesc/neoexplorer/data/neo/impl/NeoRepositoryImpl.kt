package com.francesc.neoexplorer.data.neo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.francesc.neoexplorer.data.neo.NeoRepository
import com.francesc.neoexplorer.data.neo.impl.mapper.toDomain
import com.francesc.neoexplorer.data.neo.model.AsteroidId
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import com.francesc.neoexplorer.data.neo.model.NeoFeed
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

private const val PAGE_SIZE = 20

/**
 * Like [runCatching] but rethrows [CancellationException] to preserve structured concurrency.
 * Only non-cancellation exceptions are captured as [Result.failure].
 */
private inline fun <T> runCatchingNotCancelled(block: () -> T): Result<T> =
    runCatching(block).onFailure { if (it is CancellationException) throw it }

@Inject
@ContributesBinding(AppScope::class)
class NeoRepositoryImpl(
    private val dataSource: NeoDataSource,
) : NeoRepository {
    override suspend fun getFeed(startDate: LocalDate, endDate: LocalDate?): Result<NeoFeed> =
        runCatchingNotCancelled {
            dataSource.getFeed(
                startDate = startDate.toString(),
                endDate = endDate?.toString(),
            ).toDomain()
        }

    override suspend fun lookupAsteroid(asteroidId: AsteroidId): Result<NearEarthObject> =
        runCatchingNotCancelled { dataSource.lookupAsteroid(asteroidId.value).toDomain() }

    override fun browse(): Flow<PagingData<NearEarthObject>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { NeosPagingSource(dataSource) },
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
}
