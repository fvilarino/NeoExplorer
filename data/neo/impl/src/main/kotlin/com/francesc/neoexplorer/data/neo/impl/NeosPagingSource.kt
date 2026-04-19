package com.francesc.neoexplorer.data.neo.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.francesc.neoexplorer.data.neo.impl.dto.NearEarthObjectDto

private const val STARTING_PAGE = 0

internal class NeosPagingSource(
    private val dataSource: NeoDataSource,
) : PagingSource<Int, NearEarthObjectDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NearEarthObjectDto> {
        val page = params.key ?: STARTING_PAGE
        return try {
            val response = dataSource.browse(page = page, pageSize = params.loadSize)
            LoadResult.Page(
                data = response.nearEarthObjects,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = if (page >= response.page.totalPages - 1) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NearEarthObjectDto>): Int? =
        state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
}
