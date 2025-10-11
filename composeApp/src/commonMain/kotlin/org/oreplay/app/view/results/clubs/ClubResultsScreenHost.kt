package org.oreplay.app.view.results.clubs

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
import org.oreplay.app.view.results.ResultsDestination
import org.oreplay.app.view.results.SimpleResultsScreen
import org.oreplay.app.view.results.SplitResultsScreen
import org.oreplay.app.view.results.StartTimeScreen
import org.oreplay.app.viewmodel.ClubResultsScreenComponent
import org.oreplay.app.viewmodel.HomeScreenComponent
import org.oreplay.app.viewmodel.ResultsScreenComponent

@Composable
fun ClubResultsScreenHost(
    navController: NavHostController,
    startDestination: ClubResultsDestination,
    data: List<Runner>,
    contentPadding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
    ) {
        ClubResultsDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    // TODO - Hide hidden events
                    ClubResultsDestination.CLUB_START_TIME -> StartTimeScreen(data, contentPadding)
                    ClubResultsDestination.CLUB_RESULTS -> SimpleResultsScreen(data, contentPadding)
                }
            }
        }
    }

}