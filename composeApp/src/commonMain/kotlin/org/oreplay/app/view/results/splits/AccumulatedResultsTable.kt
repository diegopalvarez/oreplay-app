package org.oreplay.app.view.results.splits

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import org.oreplay.app.model.controls.ControlList
import org.oreplay.app.model.data.Runner

@Composable
fun AccumulatedResultsTable(
    data: List<Runner>,
    scroll: ScrollState,
    verticalScroll: LazyListState,
    cellWidth: Dp,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (data.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "No runners have finished yet",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                )
            }
        } else {
            TableHeader(scrollState = scroll, header = data.first().splits, width = cellWidth)
            TableContentAccumulated(scrollState = scroll, listState = verticalScroll, data = data, width = cellWidth)
        }
    }
}