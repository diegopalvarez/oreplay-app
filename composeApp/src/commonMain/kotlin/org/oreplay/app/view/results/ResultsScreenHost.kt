package org.oreplay.app.view.results

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.data.Runner
import org.oreplay.app.viewmodel.results.ResultsScreenComponent

@Composable
fun ResultsScreenHost(
    navController: NavHostController,
    startDestination: ResultsDestination,
    component: ResultsScreenComponent,
    data: List<Runner>,
    contentPadding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
    ) {
        ResultsDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    // TODO - Hide hidden events
                    ResultsDestination.START_TIME -> StartTimeScreen(data, contentPadding)
                    ResultsDestination.RESULTS -> SimpleResultsScreen(data, contentPadding)
                    ResultsDestination.SPLITS -> SplitResultsScreen(data, contentPadding)
                }
            }
        }
    }

}