package org.oreplay.app.view.results.splits

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import org.oreplay.app.model.data.Runner

@Composable
fun AccumulatedScreen(
    data: List<Runner>,
) {
    AccumulatedResultsTable(data)
}