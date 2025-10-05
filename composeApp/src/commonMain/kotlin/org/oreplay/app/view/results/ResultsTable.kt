package org.oreplay.app.view.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.oreplay.app.model.data.Runner

@Composable
fun ResultsTable(
    data: List<Runner>,
    padding: PaddingValues,
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        TableHeader(scrollState = scroll, header = data.first().splits)
        TableContent(scrollState = scroll, data = data)
    }
}