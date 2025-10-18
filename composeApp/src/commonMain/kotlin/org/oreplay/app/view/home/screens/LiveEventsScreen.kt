package org.oreplay.app.view.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.home.HomeScreenComponent
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun LiveEventsScreen(
    component: HomeScreenComponent,
    padding: PaddingValues,
){
    val items by component.liveEventsList.collectAsState()

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }
    val scope = rememberCoroutineScope()

    component.fetchLiveEvents();

    if(items.isEmpty()){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "There are no events live today!",
            )
            // TODO - Add button to future events through Component
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(items.toList()) { event ->
            EventBox(component, event)
        }
    }
}