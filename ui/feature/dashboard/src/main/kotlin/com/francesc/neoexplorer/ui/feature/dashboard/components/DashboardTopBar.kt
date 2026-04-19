package com.francesc.neoexplorer.ui.feature.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francesc.neoexplorer.ui.feature.dashboard.R
import com.francesc.neoexplorer.ui.shared.compose.IconSizeSmall
import com.francesc.neoexplorer.ui.shared.compose.WidgetPreviews
import com.francesc.neoexplorer.ui.shared.styles.NeoExplorerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    isLoaded: Boolean,
    sortOrder: SortOrder,
    onSortOrderChange: (SortOrder) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.asteroid_watch),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {
            if (isLoaded) {
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(
                        selected = sortOrder == SortOrder.BY_DATE,
                        onClick = { onSortOrderChange(SortOrder.BY_DATE) },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                        icon = { SegmentedButtonDefaults.Icon(active = sortOrder == SortOrder.BY_DATE) },
                        label = {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = stringResource(R.string.accessibility_sort_by_date),
                                modifier = Modifier.size(IconSizeSmall),
                            )
                        },
                    )
                    SegmentedButton(
                        selected = sortOrder == SortOrder.BY_DISTANCE,
                        onClick = { onSortOrderChange(SortOrder.BY_DISTANCE) },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                        icon = { SegmentedButtonDefaults.Icon(active = sortOrder == SortOrder.BY_DISTANCE) },
                        label = {
                            Icon(
                                imageVector = Icons.Filled.NearMe,
                                contentDescription = stringResource(R.string.accessibility_sort_by_distance),
                                modifier = Modifier.size(IconSizeSmall),
                            )
                        },
                    )
                }
            }
        },
    )
}

@WidgetPreviews
@Composable
private fun DashboardTopBarByDatePreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DashboardTopBar(
                isLoaded = true,
                sortOrder = SortOrder.BY_DATE,
                onSortOrderChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@WidgetPreviews
@Composable
private fun DashboardTopBarByDistancePreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DashboardTopBar(
                isLoaded = true,
                sortOrder = SortOrder.BY_DISTANCE,
                onSortOrderChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@WidgetPreviews
@Composable
private fun DashboardTopBarLoadingPreview() {
    NeoExplorerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DashboardTopBar(
                isLoaded = false,
                sortOrder = SortOrder.BY_DATE,
                onSortOrderChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
