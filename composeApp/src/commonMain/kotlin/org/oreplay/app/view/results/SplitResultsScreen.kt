package org.oreplay.app.view.results

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.data.Runner
import org.oreplay.app.viewmodel.ResultsScreenComponent

@Composable
fun SplitResultsScreen(
    component: ResultsScreenComponent,
    data: List<Runner>,
    contentPadding: PaddingValues,
) {
    ResultsTable(data, contentPadding)
}