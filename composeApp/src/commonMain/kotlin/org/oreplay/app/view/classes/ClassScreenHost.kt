package org.oreplay.app.view.classes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.Stage
import org.oreplay.app.viewmodel.classes.ClassScreenComponent

@Composable
fun ClassScreenHost(
    navController: NavHostController,
    startDestination: ResultGroupDestination,
    component: ClassScreenComponent,
    stage: Stage,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        ResultGroupDestination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    // TODO - Hide hidden events
                    ResultGroupDestination.CLASSES -> ClassListScreen(component)
                    ResultGroupDestination.CLUBS -> ClubListScreen(component)
                }
            }
        }
    }
}