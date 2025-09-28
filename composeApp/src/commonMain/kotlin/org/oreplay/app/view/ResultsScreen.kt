package org.oreplay.app.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import org.oreplay.app.model.Class
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.Stage
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.ClassScreenEvent
import org.oreplay.app.viewmodel.ResultsScreenComponent

@Composable
fun ResultsScreen(
    component: ResultsScreenComponent,
    client: EventClient
) {
    var results by remember {
        mutableStateOf<List<RunnerResult>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    LaunchedEffect(component.raceClass) {
        client.getResults(component.stage, component.raceClass)
            .onSuccess {
                results = it
            }
            .onError {
                errorMessage = "Something went wrong"
            }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(errorMessage)
        Text(results.size.toString())

        Text("Results")

        for(runner in results){
            Text(runner.toString())
        }
    }
}