package org.oreplay.app.view.results.clubs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.Stage
import org.oreplay.app.model.data.Runner
import org.oreplay.app.view.results.StartTimeScreen
import org.oreplay.app.viewmodel.results.ClubResultsScreenComponent

@Composable
fun ClubResultsScreenHost(
    navController: NavHostController,
    startDestination: ClubResultsDestination,
    component: ClubResultsScreenComponent,
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
                    ClubResultsDestination.CLUB_START_TIME -> StartTimeScreen(component, contentPadding)
                    ClubResultsDestination.CLUB_RESULTS -> SimpleClubResultsScreen(component, contentPadding)
                }
            }
        }
    }

}