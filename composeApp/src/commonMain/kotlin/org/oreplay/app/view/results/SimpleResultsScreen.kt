package org.oreplay.app.view.results

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.controls.StatusCode
import org.oreplay.app.model.data.Runner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleResultsScreen(
    data: List<Runner>,
    contentPadding: PaddingValues,
) {
    var results by remember {
        mutableStateOf<List<RunnerResult>>(emptyList())
    }

    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    var selectedRunnerTicket by remember {
        mutableStateOf<Runner?>(null)
    }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(/*skipPartiallyExpanded = true*/)

    if(sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }
                selectedRunnerTicket = null
            },
            sheetState = sheetState,
        ) {
            // Sheet content
            selectedRunnerTicket?.let { runner ->
                ResultTicket(runner)
            }
            ?:
            scope.launch {
                sheetState.hide()
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(data) { runner ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(8.dp)
                    .clickable {
                        selectedRunnerTicket = runner
                        scope.launch {
                            sheetState.show()
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if(runner.status == StatusCode.OK){
                    if(runner.isNC){
                        Text(
                            text = "NC",
                            style = MaterialTheme.typography.displayMedium
                            )
                    }
                    else if(runner.result.position != 0L){
                        // If not, the runner hasn't arrived yet and there's no position
                        Text(
                            text = runner.result.position.toString(),
                            style = MaterialTheme.typography.displayMedium,
                        )
                    }
                }

                Text(
                    modifier = Modifier.weight(1f),
                    text = runner.fullName
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                ) {
                    // TODO - Modularize (also in splits)
                    if(runner.status == StatusCode.OK){
                        if(runner.result.position != 0L){
                            Text(
                                text = runner.result.timeSeconds.toComponents { hrs, min, sec, _ ->
                                    if (hrs > 0) {
                                        "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    } else {
                                        "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    }
                                }
                            )
                            Text(
                                text = runner.result.timeBehind.toComponents { hrs, min, sec, _ ->
                                    if (hrs > 0) {
                                        "+${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    } else {
                                        "+${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    }
                                }
                            )
                        }
                    }
                    else{
                        Text(
                            text = runner.status.toString(),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                }
            }
        }
    }
}