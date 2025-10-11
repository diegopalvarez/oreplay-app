package org.oreplay.app.view.classes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.oreplay.app.model.Class
import org.oreplay.app.model.Club
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.ClassScreenComponent
import org.oreplay.app.viewmodel.ClassScreenEvent

@Composable
fun ClubListScreen(
    component: ClassScreenComponent,
    client: EventClient,
) {
    var clubList by remember {
        mutableStateOf<List<Club>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    LaunchedEffect(component.stage) {
        client.getClubs(component.stage)
            .onSuccess {
                clubList = it
            }
            .onError {
                errorMessage = "Something went wrong"

            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(clubList) { club ->
            Button(
                onClick = {
                    component.onClubEvent(ClassScreenEvent.ClickClubEvent, component.stage, club)
                }
            ) {
                Text(club.shortName)
            }
        }
    }
}