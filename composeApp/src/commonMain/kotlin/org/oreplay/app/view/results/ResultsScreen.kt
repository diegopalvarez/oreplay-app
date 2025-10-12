package org.oreplay.app.view.results

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.data.Runner
import org.oreplay.app.model.data.createRunners
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.results.ResultsScreenComponent

enum class ResultsDestination(
    val route: String,
    val label: String,
    val contentDescription: String,
    val icon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    START_TIME("startTime", "Start Time", "Start times for the runners of the class", Icons.Filled.Schedule, Icons.Outlined.Schedule),
    RESULTS("results", "Results", "Results of the runner of the class", Icons.Filled.Leaderboard, Icons.Outlined.Leaderboard),
    SPLITS("splits", "Splits", "Detailed results with splits of the runners of the class", Icons.Filled.AvTimer, Icons.Outlined.AvTimer),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    component: ResultsScreenComponent
) {
    val navController = rememberNavController()
    val startDestination = ResultsDestination.RESULTS
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    val runnerList by component.runnerList.collectAsState()
    component.getRunners();

    // Keep the selected tab updated
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        val index = ResultsDestination.entries.indexOfFirst { it.route == currentRoute }
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
                ResultsDestination.entries.forEachIndexed { index, destination ->
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
            ResultsScreenHost(navController, startDestination, component, runnerList, contentPadding)
    }
}

/**
@Composable
fun ResultsScreen(
    component: ResultsScreenComponent,
    client: EventClient
) {
    var results by remember {
        mutableStateOf<List<RunnerResult>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    LaunchedEffect(component.raceClass) {
        client.getResults(component.stage, component.raceClass)
            .onSuccess {
                results = it
            }
            .onError {
                errorMessage = "Something went wrong"
            }
    }

    val headers = listOf("ID", "Name", "Age", "City", "Country", "Status")
    val data = List(25) { index ->
        listOf(
            (index + 1).toString(),
            "User $index",
            (20 + index).toString(),
            "City $index",
            "Country $index",
            if (index % 2 == 0) "Active" else "Inactive"
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { contentPadding ->
        ResultsTable(headers, data, contentPadding)
    }
}
*/