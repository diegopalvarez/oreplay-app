package org.oreplay.app.view.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.data.Runner
import org.oreplay.app.viewmodel.ResultsScreenComponent

@Composable
fun StartTimeScreen(
    data: List<Runner>,
    contentPadding: PaddingValues,
) {
    var results by remember {
        mutableStateOf<List<Runner>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    results = data.sortedWith(compareBy(nullsLast()) { it.startTime })

    LazyColumn(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        items(results) { runner ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = runner.fullName
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                ) {
                    if(runner.startTime == null){
                        Text(runner.status.toString())
                    }
                    else{
                        Text(
                            text = runner.startTime.toLocalDateTime(TimeZone.currentSystemDefault()).time.toString(),
                        )
                    }
                    runner.SICard?.let {
                        Text(
                            text = it
                        )
                    }
                }
            }
        }
    }
}