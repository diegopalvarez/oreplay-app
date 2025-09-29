package org.oreplay.app.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.HomeScreenComponent
import org.oreplay.app.viewmodel.HomeScreenEvent

@Composable
fun FutureEventsScreen(component: HomeScreenComponent, client: EventClient){
    var eventList by remember {
        mutableStateOf<List<Event>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        scope.launch {
            client.getFutureEvents()
                .onSuccess {
                    eventList = it
                }
                .onError {
                    errorMessage = "Something went wrong"
                }
        }

        Text(errorMessage)
        Text(eventList.size.toString())

        Text("Screen A")

        for(event in eventList){
            Button(onClick = {
                component.onEvent(HomeScreenEvent.ClickEvent, event)
            }){
                Text(event.description)
            }
        }
    }
}