package com.francesc.neoexplorer.data.neo.impl

import androidx.paging.PagingSource
import com.francesc.neoexplorer.data.neo.model.AsteroidId
import com.francesc.neoexplorer.data.neo.model.NearEarthObject
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NeosPagingSourceTest {

  private val refreshParams =
    PagingSource.LoadParams.Refresh<Int>(key = null, loadSize = 20, placeholdersEnabled = false)

  // region successful load
  @Test
  fun `load returns Page with mapped domain objects`() = runTest {
    val dto = nearEarthObjectDto(id = "42", name = "2099 AB1")
    val dataSource =
      FakeNeoDataSource().apply {
        browseResponse = neoBrowseResponse(neos = listOf(dto), page = 0, totalPages = 3)
      }

    val result = NeosPagingSource(dataSource).load(refreshParams)

    assertTrue(result is PagingSource.LoadResult.Page)
    val page = result as PagingSource.LoadResult.Page<Int, NearEarthObject>
    assertEquals(1, page.data.size)
    assertEquals(AsteroidId("42"), page.data.first().id)
    assertEquals("2099 AB1", page.data.first().name)
  }

  @Test
  fun `load sets prevKey to null for first page`() = runTest {
    val dataSource =
      FakeNeoDataSource().apply {
        browseResponse = neoBrowseResponse(totalPages = 5)
      }

    val result = NeosPagingSource(dataSource).load(refreshParams) as PagingSource.LoadResult.Page

    assertEquals(null, result.prevKey)
  }

  @Test
  fun `load sets nextKey to null on last page`() = runTest {
    val dataSource =
      FakeNeoDataSource().apply {
        browseResponse = neoBrowseResponse(page = 4, totalPages = 5)
      }
    val lastPageParams =
      PagingSource.LoadParams.Append<Int>(key = 4, loadSize = 20, placeholdersEnabled = false)

    val result = NeosPagingSource(dataSource).load(lastPageParams) as PagingSource.LoadResult.Page

    assertEquals(null, result.nextKey)
  }

  @Test
  fun `load sets correct nextKey for intermediate page`() = runTest {
    val dataSource =
      FakeNeoDataSource().apply {
        browseResponse = neoBrowseResponse(page = 1, totalPages = 5)
      }
    val midPageParams =
      PagingSource.LoadParams.Append<Int>(key = 1, loadSize = 20, placeholdersEnabled = false)

    val result = NeosPagingSource(dataSource).load(midPageParams) as PagingSource.LoadResult.Page

    assertEquals(2, result.nextKey)
  }

  // endregion
  // region mapping failure → LoadResult.Error (regression guard for B3)

  @Test
  fun `load returns Error when velocity string is malformed`() = runTest {
    // Regression guard for B3: a non-numeric velocity string from the API must surface
    // as LoadResult.Error (paging recoverable error) rather than crashing the app.
    val badDto =
      nearEarthObjectDto(
        closeApproachData = listOf(closeApproachDataDto(velocityKmPerSecond = "not_a_number"))
      )
    val dataSource =
      FakeNeoDataSource().apply {
        browseResponse = neoBrowseResponse(neos = listOf(badDto))
      }

    val result = NeosPagingSource(dataSource).load(refreshParams)

    assertTrue(
      "Expected LoadResult.Error but got $result",
      result is PagingSource.LoadResult.Error,
    )
    assertTrue(
      "Expected NumberFormatException",
      (result as PagingSource.LoadResult.Error).throwable is NumberFormatException,
    )
  }

  @Test
  fun `load returns Error when miss distance km string is malformed`() = runTest {
    val badDto =
      nearEarthObjectDto(closeApproachData = listOf(closeApproachDataDto(missDistanceKm = "N/A")))
    val dataSource =
      FakeNeoDataSource().apply {
        browseResponse = neoBrowseResponse(neos = listOf(badDto))
      }

    val result = NeosPagingSource(dataSource).load(refreshParams)

    assertTrue(result is PagingSource.LoadResult.Error)
    assertTrue((result as PagingSource.LoadResult.Error).throwable is NumberFormatException)
  }

  @Test
  fun `load returns Error when close approach date string is malformed`() = runTest {
    val badDto =
      nearEarthObjectDto(
        closeApproachData = listOf(closeApproachDataDto(closeApproachDate = "not-a-date"))
      )
    val dataSource =
      FakeNeoDataSource().apply {
        browseResponse = neoBrowseResponse(neos = listOf(badDto))
      }

    val result = NeosPagingSource(dataSource).load(refreshParams)

    assertTrue(result is PagingSource.LoadResult.Error)
  }

  // endregion
  // region network failure

  @Test
  fun `load returns Error when data source throws`() = runTest {
    val dataSource =
      FakeNeoDataSource().apply {
        error = RuntimeException("network failure")
      }

    val result = NeosPagingSource(dataSource).load(refreshParams)

    assertTrue(result is PagingSource.LoadResult.Error)
    assertEquals("network failure", (result as PagingSource.LoadResult.Error).throwable.message)
  }

  // endregion
}
