package org.oreplay.app.view.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.oreplay.app.model.EventClient
import org.oreplay.app.view.home.searchbar.HomeSearchBar
import org.oreplay.app.viewmodel.home.HomeScreenComponent
import org.oreplay.app.viewmodel.home.HomeScreenEvent

enum class Destination(
    val route: String,
    val label: String,
    val contentDescription: String
) {
    PAST_EVENTS("pastEvents", "Past Events", "Events that took place in the past"),
    LIVE_EVENTS("liveEvents", "Live Events", "Events live today"),
    FUTURE_EVENTS("futureEvents", "Future Events", "Events that will take place in the future"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    component: HomeScreenComponent
) {
    // Create Navigation Bar
    val navController = rememberNavController()
    val startDestination = Destination.LIVE_EVENTS
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    // Keep the selected tab updated
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        val index = Destination.entries.indexOfFirst { it.route == currentRoute }
        if (index != -1 && index != selectedDestination) {
            selectedDestination = index
        }
    }

    // Search Bar functionality
    val filterState by component.uiFilterState.collectAsState()
    val searchResults by component.combinedFilteredEvents.collectAsState()

    // Screen content
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        // Top Bar
        topBar = {
            HomeSearchBar(
                query = filterState.query,
                onQueryChange = { component.updateSearchQuery(it) },
                onSearch = { /* Handle search submission */ },
                searchResults = searchResults,
                onResultClick = { clickedItem ->
                    component.onEvent(HomeScreenEvent.ClickEvent, clickedItem)
                },
                // Customize appearance with optional parameters
                placeholder = { Text("Search events") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "More options",
                        modifier = Modifier.clickable { component.updateSearchQuery("") })
                },
                leadingContent = { Icon(Icons.Filled.Star, contentDescription = "Starred item") },
                selectedFilters = filterState.selectedFilters,
                onToggleFilter = { filterTag ->
                    // Toggle filter: add/remove from selectedFilters
                    val current = filterState.selectedFilters.toMutableSet()
                    if (current.contains(filterTag)) current.remove(filterTag) else current.add(filterTag)
                    component.updateSelectedFilters(current)
                },
                selectedDate = filterState.selectedDate,
                onDateSelected = { component.updateSelectedDate(it) }
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            SecondaryTabRow(
                selectedTabIndex = selectedDestination,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
            ) {
                Destination.entries.forEachIndexed { index, destination ->
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
            HomeScreenHost(navController, startDestination, component, contentPadding)
        }
    }
}