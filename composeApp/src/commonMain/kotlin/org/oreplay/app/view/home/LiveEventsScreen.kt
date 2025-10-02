package org.oreplay.app.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.HomeScreenComponent
import org.oreplay.app.viewmodel.HomeScreenEvent
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun LiveEventsScreen(
    component: HomeScreenComponent,
    client: EventClient,
    padding: PaddingValues,
){
    var eventList by remember {
        mutableStateOf<List<Event>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }
    val scope = rememberCoroutineScope()

    scope.launch {
        client.getTodayEvents()
            .onSuccess {
                eventList = it
            }
            .onError {
                errorMessage = "Something went wrong"
            }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.
        fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(eventList) { event ->
            EventBox(component, event)
        }
    }
}