package org.oreplay.app.view.stages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.EventDetails
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.EventScreenComponent
import org.oreplay.app.viewmodel.EventScreenEvent

@Composable
fun EventScreen(event: Event, component: EventScreenComponent, client: EventClient) {
    var eventDetails by remember {
        mutableStateOf<EventDetails?>(null)
    }
    LaunchedEffect(event) {
        client.getEventDetails(event)
            .onSuccess {
                eventDetails = it
            }
    }
    /** TODO - Handle going back from skipping: loop
    eventDetails?.let {
        if(it.stages.size == 1) {
            component.onEvent(EventScreenEvent.ClickEvent, it.stages[0]);
        }
    }
    */
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            stickyHeader {
                StageDetails(event)
            }

            eventDetails?.let {
                items(it.stages) { stage ->
                    StageItem(component, stage)
                }
            }
        }
    }
}