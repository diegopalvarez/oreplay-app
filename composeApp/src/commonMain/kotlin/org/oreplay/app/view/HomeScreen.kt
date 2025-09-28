package org.oreplay.app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.HomeScreenComponent
import org.oreplay.app.viewmodel.HomeScreenEvent
import org.oreplay.app.viewmodel.RootComponent

@Composable
fun HomeScreen(component: HomeScreenComponent, client: EventClient){
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
            client.getEvents()
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