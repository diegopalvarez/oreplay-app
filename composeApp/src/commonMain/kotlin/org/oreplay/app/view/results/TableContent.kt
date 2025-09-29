package org.oreplay.app.view.results

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TableContent(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    data: List<List<String>>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(data) { row ->
            Row (
                modifier = Modifier
                    .horizontalScroll(scrollState)
            ) {
                row.forEach { cell ->
                    Text(
                        text = cell,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}