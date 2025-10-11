package org.oreplay.app.view.classes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.Stage
import org.oreplay.app.view.home.Destination
import org.oreplay.app.view.home.FutureEventsScreen
import org.oreplay.app.view.home.LiveEventsScreen
import org.oreplay.app.view.home.PastEventsScreen
import org.oreplay.app.viewmodel.ClassScreenComponent

@Composable
fun ClassScreenHost(
    navController: NavHostController,
    startDestination: ResultGroupDestination,
    component: ClassScreenComponent,
    stage: Stage,
    client: EventClient,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        ResultGroupDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    // TODO - Hide hidden events
                    ResultGroupDestination.CLASSES -> ClassListScreen(component, client)
                    ResultGroupDestination.CLUBS -> ClubListScreen(component, client)
                }
            }
        }
    }
}