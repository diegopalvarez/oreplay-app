package org.oreplay.app.view.results.clubs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.data.Runner
import org.oreplay.app.model.data.createClubRunners
import org.oreplay.app.model.data.createRunners
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.view.results.ResultsDestination
import org.oreplay.app.view.results.ResultsScreenHost
import org.oreplay.app.viewmodel.ClubResultsScreenComponent

enum class ClubResultsDestination(
    val route: String,
    val label: String,
    val contentDescription: String,
    val icon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    CLUB_START_TIME("startTime", "Start Time", "Start times for the runners of the club", Icons.Filled.Schedule, Icons.Outlined.Schedule),
    CLUB_RESULTS("results", "Results", "Results of the runner of the club", Icons.Filled.Leaderboard, Icons.Outlined.Leaderboard),
}

@Composable
fun ClubResultsScreen(
    component: ClubResultsScreenComponent,
    client: EventClient
) {
    val navController = rememberNavController()
    val startDestination = ClubResultsDestination.CLUB_RESULTS
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    var data by remember {
        mutableStateOf<List<RunnerResult>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    var runnerList by remember {
        mutableStateOf<List<Runner>>(emptyList())
    }

    LaunchedEffect(component.club) {
        client.getClubResults(component.stage, component.club)
            .onSuccess {
                data = it
                runnerList = createClubRunners(data, component.stage, client)
            }
            .onError {
                errorMessage = "Something went wrong"
            }
    }

    // Keep the selected tab updated
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        val index = ClubResultsDestination.entries.indexOfFirst { it.route == currentRoute }
        if (index != -1 && index != selectedDestination) {
            selectedDestination = index
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                ClubResultsDestination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                            }
                            selectedDestination = index
                        },
                        icon = {
                            if (selectedDestination == index) {
                                Icon(
                                    destination.icon,
                                    contentDescription = destination.contentDescription
                                )
                            }
                            else{
                                Icon(
                                    destination.unselectedIcon,
                                    contentDescription = destination.contentDescription
                                )
                            }

                        },
                        label = {
                            Text(
                                destination.label,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                    )

                }
            }
        }
    ) { contentPadding ->
        ClubResultsScreenHost(navController, startDestination, runnerList, contentPadding)
    }
}