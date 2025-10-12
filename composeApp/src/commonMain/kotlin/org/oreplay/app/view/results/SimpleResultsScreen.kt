package org.oreplay.app.view.results

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            sheetState = sheetState
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
    ) {
        items(data) { runner ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        selectedRunnerTicket = runner
                        scope.launch {
                            sheetState.show()
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if(runner.status == StatusCode.OK){
                    if(runner.isNC){
                        Text(
                            text = "NC",
                            style = MaterialTheme.typography.displayMedium
                            )
                    }
                    else{
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