package org.oreplay.app.view.home.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.EventClient
import org.oreplay.app.viewmodel.home.HomeScreenComponent

@Composable
fun HomeScreenHost(
    navController: NavHostController,
    startDestination: Destination,
    component: HomeScreenComponent,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    // TODO - Hide hidden events
                    Destination.LIVE_EVENTS -> LiveEventsScreen(component, contentPadding)
                    Destination.PAST_EVENTS -> PastEventsScreen(component, contentPadding)
                    Destination.FUTURE_EVENTS -> FutureEventsScreen(component, contentPadding)
                }
            }
        }
    }

}