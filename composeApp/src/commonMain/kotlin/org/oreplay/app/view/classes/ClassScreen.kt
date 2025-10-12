package org.oreplay.app.view.classes

    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.SecondaryTabRow
    import androidx.compose.material3.Tab
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.navigation.NavGraph.Companion.findStartDestination
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.compose.rememberNavController
    import org.oreplay.app.viewmodel.classes.ClassScreenComponent

enum class ResultGroupDestination(
    val route: String,
    val label: String,
    val contentDescription: String
) {
    CLASSES("classes", "Classes", "Classes for the race"),
    CLUBS("clubs", "Clubs", "Clubs participating in the race"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassScreen(component: ClassScreenComponent) {
    val navController = rememberNavController()
    val startDestination = ResultGroupDestination.CLASSES
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    // Keep the selected tab updated
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        val index = ResultGroupDestination.entries.indexOfFirst { it.route == currentRoute }
        if (index != -1 && index != selectedDestination) {
            selectedDestination = index
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            SecondaryTabRow(
                selectedTabIndex = selectedDestination,
                modifier = Modifier.fillMaxWidth(),
            ) {
                ResultGroupDestination.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route){
                                launchSingleTop = true
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                            }
                            selectedDestination = index
                        },
                        text = {
                            Text(
                                destination.label,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                    )

                }
            }
            ClassScreenHost(navController, startDestination, component, component.stage)
        }
    }
}