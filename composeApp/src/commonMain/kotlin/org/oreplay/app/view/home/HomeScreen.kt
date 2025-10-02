package org.oreplay.app.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.rememberNavController
import org.oreplay.app.model.EventClient
import org.oreplay.app.viewmodel.HomeScreenComponent

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
    component: HomeScreenComponent,
    client: EventClient
) {
    val navController = rememberNavController()
    val startDestination = Destination.LIVE_EVENTS
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
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
                Destination.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
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
            HomeScreenHost(navController, startDestination, component, client, contentPadding)
        }
    }
}