package com.francesc.neoexplorer.data.neo.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.francesc.neoexplorer.data.neo.impl.mapper.toDomain
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import kotlinx.coroutines.CancellationException

private const val STARTING_PAGE = 0

internal class NeosPagingSource(private val dataSource: NeoDataSource) :
  PagingSource<Int, NearEarthObject>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NearEarthObject> {
    val page = params.key ?: STARTING_PAGE
    return try {
      val response = dataSource.browse(page = page, pageSize = params.loadSize)
      // toDomain() is mapped here (inside the try/catch) so that malformed numeric strings
      // or unparseable dates from the API surface as LoadResult.Error rather than crashing.
      LoadResult.Page(
        data = response.nearEarthObjects.map { it.toDomain() },
        prevKey = if (page == STARTING_PAGE) null else page - 1,
        nextKey = if (page >= response.page.totalPages - 1) null else page + 1,
      )
    } catch (e: Exception) {
      if (e is CancellationException) throw e
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, NearEarthObject>): Int? =
    state.anchorPosition?.let { anchor ->
      state.closestPageToPosition(anchor)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
    }
}
