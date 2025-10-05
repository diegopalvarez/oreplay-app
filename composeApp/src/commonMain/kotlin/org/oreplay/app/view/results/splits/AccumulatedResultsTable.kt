package org.oreplay.app.view.results.splits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.oreplay.app.model.data.Runner

@Composable
fun AccumulatedResultsTable(
    data: List<Runner>,
) {
    val scroll = rememberScrollState()
    var verticalScroll = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TableHeader(scrollState = scroll, header = data.first().splits)
        TableContentAccumulated(scrollState = scroll, listState = verticalScroll, data = data)
    }
}