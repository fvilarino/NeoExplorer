package com.francesc.neoexplorer.data.neo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
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
import kotlinx.datetime.LocalDate

private const val PAGE_SIZE = 20

/**
 * Like [runCatching] but rethrows [CancellationException] to preserve structured concurrency. Only
 * non-cancellation exceptions are captured as [Result.failure].
 */
private inline fun <T> runCatchingNotCancelled(block: () -> T): Result<T> =
  runCatching(block).onFailure { if (it is CancellationException) throw it }

@Inject
@ContributesBinding(AppScope::class)
class NeoRepositoryImpl(
  private val dataSource: NeoDataSource,
  private val asteroidLocalDataSource: AsteroidLocalDataSource,
  private val feedLocalDataSource: FeedLocalDataSource,
) : NeoRepository {
  override suspend fun getFeed(startDate: LocalDate, endDate: LocalDate?): Result<NeoFeed> {
    feedLocalDataSource.getFeed(startDate, endDate)?.let {
      return Result.success(it)
    }
    return runCatchingNotCancelled {
      dataSource
        .getFeed(
          startDate = startDate.toString(),
          endDate = endDate?.toString(),
        )
        .toDomain()
    }
      .onSuccess { feedLocalDataSource.putFeed(startDate, endDate, it) }
  }

  override suspend fun lookupAsteroid(asteroidId: AsteroidId): Result<NearEarthObject> {
    asteroidLocalDataSource.getAsteroid(asteroidId)?.let {
      return Result.success(it)
    }
    return runCatchingNotCancelled {
      dataSource.lookupAsteroid(asteroidId.value).toDomain()
    }
      .onSuccess { asteroidLocalDataSource.putAsteroid(it) }
  }

  override fun browse(): Flow<PagingData<NearEarthObject>> =
    Pager(
        // initialLoadSize is pinned to PAGE_SIZE so every request uses the same `size`.
        // NeosPagingSource keys pages by index, so a larger first load (the Paging default of
        // pageSize * 3) would desync the page math and re-fetch/duplicate items.
        config =
          PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = false,
          ),
        pagingSourceFactory = { NeosPagingSource(dataSource) },
      )
      .flow
}
