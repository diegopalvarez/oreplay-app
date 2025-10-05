package org.oreplay.app.view.results

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.data.Runner
import org.oreplay.app.view.home.Destination
import org.oreplay.app.view.home.FutureEventsScreen
import org.oreplay.app.view.home.LiveEventsScreen
import org.oreplay.app.view.home.PastEventsScreen
import org.oreplay.app.viewmodel.HomeScreenComponent
import org.oreplay.app.viewmodel.ResultsScreenComponent

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
        startDestination = startDestination.route
    ) {
        ResultsDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    // TODO - Hide hidden events
                    ResultsDestination.START_TIME -> StartTimeScreen(component, data, contentPadding)
                    ResultsDestination.RESULTS -> SimpleResultsScreen(component, data, contentPadding)
                    ResultsDestination.SPLITS -> SplitResultsScreen(component, data, contentPadding)
                }
            }
        }
    }

}