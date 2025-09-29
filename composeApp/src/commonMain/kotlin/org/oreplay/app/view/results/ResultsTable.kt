package org.oreplay.app.view.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResultsTable(
    header: List<String>,
    data: List<List<String>>
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TableHeader(scrollState = scroll, header = header)
        TableContent(scrollState = scroll, data = data)
    }
}