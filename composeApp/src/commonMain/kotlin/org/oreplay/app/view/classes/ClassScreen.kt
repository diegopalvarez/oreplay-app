package org.oreplay.app.view.classes

    import androidx.compose.animation.Crossfade
    import androidx.compose.animation.animateContentSize
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.windowInsetsPadding
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.ArrowBack
    import androidx.compose.material.icons.filled.Close
    import androidx.compose.material.icons.filled.Search
    import androidx.compose.material3.CenterAlignedTopAppBar
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.SecondaryTabRow
    import androidx.compose.material3.Tab
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextField
    import androidx.compose.material3.TopAppBarDefaults
    import androidx.compose.material3.rememberTopAppBarState
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.layout.layout
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.navigation.NavGraph.Companion.findStartDestination
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.compose.rememberNavController
    import org.oreplay.app.viewmodel.classes.ClassScreenComponent
    import kotlin.math.roundToInt

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

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val query by component.searchFilter.collectAsState()
    var isSearch by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Crossfade(
                modifier = Modifier.animateContentSize(),
                targetState = isSearch,
                label = "Search"
            ){ target ->
                if(!target){
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        title = {
                            Text(
                                text = component.stage.description,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                component.goBack()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Go back to the stages screen"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                isSearch = true
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Filter classes and clubs"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                    )
                }
                else{
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                            .background(MaterialTheme.colorScheme.surface)
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                val height = placeable.height * (1 - scrollBehavior.state.collapsedFraction)
                                layout(placeable.width, height.roundToInt()) {
                                    placeable.place(0, 0)
                                }
                            },
                        value = query,
                        placeholder = { Text("Enter card name") },
                        onValueChange = { component.updateSearchQuery(it) },
                        leadingIcon = {
                            IconButton(onClick = {
                                isSearch = false
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Go back to the stages screen"
                                )
                            }
                        },
                        trailingIcon = {
                            if (query.isNotBlank()) {
                                IconButton(onClick = {
                                    component.updateSearchQuery("")
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Remove filter"
                                    )
                                }
                            }
                        }
                    )
                }

            }
        },
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