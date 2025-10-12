package org.oreplay.app.view.stages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.EventDetails
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.stages.EventScreenComponent

@Composable
fun EventScreen(component: EventScreenComponent) {

    val eventDetails by component.eventDetails.collectAsState()
    component.getEventDetails();
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
                StageDetails(component.event)
            }

            eventDetails?.let {
                items(it.stages) { stage ->
                    StageItem(component, stage)
                }
            }
        }
    }
}