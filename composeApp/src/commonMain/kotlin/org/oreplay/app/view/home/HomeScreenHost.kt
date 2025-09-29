package org.oreplay.app.view.home

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.EventClient
import org.oreplay.app.viewmodel.HomeScreenComponent

@Composable
fun HomeScreenHost(
    navController: NavHostController,
    startDestination: Destination,
    component: HomeScreenComponent,
    client: EventClient
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.LIVE_EVENTS -> LiveEventsScreen(component, client)
                    Destination.PAST_EVENTS -> PastEventsScreen(component, client)
                    Destination.FUTURE_EVENTS -> FutureEventsScreen(component, client)
                }
            }
        }
    }

}