package org.oreplay.app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.launch
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.EventDetails
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.EventScreenComponent
import org.oreplay.app.viewmodel.EventScreenEvent
import org.oreplay.app.viewmodel.HomeScreenEvent
import org.oreplay.app.viewmodel.RootComponent

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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Event Screen: ${event.description}")
        eventDetails?.let {
            Text("Event Stages: ${it.stages.size.toString()}")

            for(event in it.stages){
                Button(onClick = {
                    component.onEvent(EventScreenEvent.ClickEvent, event)
                }){
                    Text(event.description)
                }
            }
        }

        Button(onClick = {
            component.goBack()
        }) {
            Text("Go Back")
        }
    }
}