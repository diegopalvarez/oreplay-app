package org.oreplay.app.view.home

import androidx.compose.foundation.layout.PaddingValues
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
    client: EventClient,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.LIVE_EVENTS -> LiveEventsScreen(component, client, contentPadding)
                    Destination.PAST_EVENTS -> PastEventsScreen(component, client, contentPadding)
                    Destination.FUTURE_EVENTS -> FutureEventsScreen(component, client, contentPadding)
                }
            }
        }
    }

}