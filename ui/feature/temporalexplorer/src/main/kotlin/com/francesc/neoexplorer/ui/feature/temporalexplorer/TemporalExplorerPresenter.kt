package com.francesc.neoexplorer.ui.feature.temporalexplorer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.francesc.neoexplorer.core.clock.DateProvider
import com.francesc.neoexplorer.ui.feature.temporalexplorer.components.TemporalExplorerScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.datetime.LocalDate

@CircuitInject(TemporalExplorerScreen::class, AppScope::class)
@Inject
class TemporalExplorerPresenter(private val dateProvider: DateProvider) :
  Presenter<TemporalExplorerUiState> {

  @Composable
  override fun present(): TemporalExplorerUiState {
    val today = remember { dateProvider.today() }
    var startDate by remember { mutableStateOf<LocalDate?>(today) }
    var endDate by remember { mutableStateOf<LocalDate?>(today) }
    var loadingState by remember { mutableStateOf(TemporalExplorerLoadingState.IDLE) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    return TemporalExplorerUiState(
      loadingState = loadingState,
      startDate = startDate,
      endDate = endDate,
      errorMessage = errorMessage,
      eventSink = { event ->
        when (event) {
          is TemporalExplorerEvent.SetStartDate -> startDate = event.date
          is TemporalExplorerEvent.SetEndDate -> endDate = event.date
          TemporalExplorerEvent.Search -> {
            // TODO: trigger feed fetch for [startDate, endDate] range
            loadingState = TemporalExplorerLoadingState.IDLE
          }
        }
      },
    )
  }
}
