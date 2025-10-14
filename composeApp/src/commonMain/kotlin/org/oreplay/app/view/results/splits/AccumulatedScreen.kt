package org.oreplay.app.view.results.splits

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import org.oreplay.app.model.data.Runner

@Composable
fun AccumulatedScreen(
    data: List<Runner>,
    scroll: ScrollState,
    verticalScroll: LazyListState,
) {
    // TODO - Say that official version works wrong, or am I doing something wrong?
    AccumulatedResultsTable(data, scroll, verticalScroll)
}