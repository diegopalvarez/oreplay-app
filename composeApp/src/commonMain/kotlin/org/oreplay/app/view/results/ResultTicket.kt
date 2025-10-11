package org.oreplay.app.view.results

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.oreplay.app.model.controls.ControlItem
import org.oreplay.app.model.controls.StatusCode
import org.oreplay.app.model.data.Runner
import kotlin.time.Duration

@Composable
fun ResultTicket(
    runner: Runner,
) {
    var item: ControlItem? = runner.splits.first
    val controlList: ArrayList<ControlItem> = ArrayList()
    while(item != null) {
        controlList.add(item)
        item = item.next
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        // Runner Information
        item {
            Text(
                text = runner.fullName
            )
            Text(
                text = runner.club.shortName,
            )
            Text(
                text = runner.startTime.toLocalDateTime(TimeZone.currentSystemDefault()).time.toString(),
            )

            if (runner.status == StatusCode.OK) {
                if (runner.isNC) {
                    Text(
                        text = "NC",
                        style = MaterialTheme.typography.displayMedium
                    )
                } else {
                    Text(
                        text = runner.result.position.toString(),
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
            }

            if (runner.status == StatusCode.OK) {
                Text(
                    text = runner.result.timeSeconds.toComponents { hrs, min, sec, _ ->
                        if (hrs > 0) {
                            "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                        } else {
                            "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                        }
                    }
                )
            } else {
                Text(
                    text = runner.status.toString(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        // Headers
        items(controlList) { control ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Control Information
                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp)
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
                        .width(100.dp)
                        .padding(8.dp)
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

                // Cummulative Time
                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp)
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