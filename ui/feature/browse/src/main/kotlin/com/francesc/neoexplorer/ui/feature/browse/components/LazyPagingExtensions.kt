package com.francesc.neoexplorer.ui.feature.browse.components

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.slack.circuit.retained.rememberRetained
import kotlinx.coroutines.flow.Flow

/**
 * Collects this [Flow] of [PagingData] as [LazyPagingItems], retaining the underlying pager across
 * recompositions and Circuit back-stack navigation via [rememberRetained].
 */
@Composable
internal fun <T : Any> Flow<PagingData<T>>.collectAsRetainedLazyPagingItems(): LazyPagingItems<T> {
  val retainedFlow = rememberRetained { this }
  return retainedFlow.collectAsLazyPagingItems()
}
