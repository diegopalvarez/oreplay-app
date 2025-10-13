package org.oreplay.app.view.results.clubs

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.Stage
import org.oreplay.app.model.controls.ControlItem
import org.oreplay.app.model.controls.StatusCode
import org.oreplay.app.model.data.Runner
import org.oreplay.app.model.data.createClubRunners
import org.oreplay.app.model.data.createRunners
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.viewmodel.results.ClubResultsScreenComponent
import kotlin.time.Duration

@Composable
fun ClubResultTicket(
    inputRunner: Runner,
    component: ClubResultsScreenComponent,
) {
    val runnerData by component.ticketRunner.collectAsState()
    component.getRunnerResults(inputRunner);

    if (runnerData == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val runner = runnerData!!

    var item: ControlItem? = runner.splits.first
    val controlList: ArrayList<ControlItem> = ArrayList()
    while(item != null) {
        controlList.add(item)
        item = item.next
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Runner Information
        item {
            Text(
                text = runner.fullName
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = runner.club.shortName,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = runner.runnerClass.shortName,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                )
            }

            if(runner.startTime == null){
                Text(runner.status.toString())
            }
            else{
                Text(
                    text = runner.startTime?.toLocalDateTime(TimeZone.currentSystemDefault())?.time.toString(),
                )
            }


            if (runner.status == StatusCode.OK) {
                if (runner.isNC) {
                    Text(
                        text = "NC",
                        style = MaterialTheme.typography.displayMedium
                    )
                } else if (runner.result.finishTime != null){
                    Text(
                        text = runner.result.position.toString(),
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
            }

            if (runner.status == StatusCode.OK) {
                if(runner.result.finishTime != null){
                    Text(
                        text = runner.result.timeSeconds.toComponents { hrs, min, sec, _ ->
                            if (hrs > 0) {
                                "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                            } else {
                                "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                            }
                        }
                    )
                }
            } else {
                Text(
                    text = runner.status.toString(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        if(runner.result.finishTime == null && runner.status == StatusCode.OK || runner.status == StatusCode.DNS) {
            item {
                Text(
                    text = "No chip reading"
                )
            }
            return@LazyColumn
        }

        // Titles
        item{
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Control",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Split",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Cumulative",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }

        // Headers
        items(controlList) { control ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Control Information
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if(control.isFinishControl){
                        Text(
                            text = "Fin"
                        )
                    }
                    else{
                        Text(
                            text = (controlList.indexOf(control) + 1).toString() + " (" + control.stationNumber + ")",
                        )
                    }

                }
                // Split Time
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (control.splitTime == Duration.INFINITE) {
                        Text(
                            text = "--"
                        )
                    } else {
                        Text(
                            text = control.splitTime.toComponents { hrs, min, sec, _ ->
                                if (hrs > 0) {
                                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                } else {
                                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                }
                            }
                                    + " (" + control.position + ")"
                        )
                    }

                    if (control.timeBehind == Duration.INFINITE) {
                        Text(
                            text = "--"
                        )
                    } else {

                        Text(
                            text = "+" + control.timeBehind.toComponents { hrs, min, sec, _ ->
                                if (hrs > 0) {
                                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                } else {
                                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                }
                            }
                        )
                    }
                }

                // Cumulative Time
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if(control.accumulatedTime == Duration.INFINITE){
                        Text(
                            text = "--"
                        )
                    }
                    else if(control.isAccumulatedError){
                        Text(
                            text = control.accumulatedTime.toComponents { hrs, min, sec, _ ->
                                if (hrs > 0) {
                                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                } else {
                                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                }
                            }
                        )
                    }
                    else{
                        Text(
                            text = control.accumulatedTime.toComponents { hrs, min, sec, _ ->
                                if (hrs > 0) {
                                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                } else {
                                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                }
                            }
                                    + " (" + control.accumulatedPosition + ")"
                        )
                    }

                    if(control.accumulatedTimeBehind == Duration.INFINITE){
                        Text(
                            text = "--"
                        )
                    }
                    else if(!control.isAccumulatedError){
                        Text(
                            text = "+" + control.accumulatedTimeBehind.toComponents { hrs, min, sec, _ ->
                                if (hrs > 0) {
                                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                } else {
                                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}