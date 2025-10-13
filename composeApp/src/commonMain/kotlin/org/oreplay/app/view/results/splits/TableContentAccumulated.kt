    package org.oreplay.app.view.results.splits

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.controls.ControlItem
import org.oreplay.app.model.controls.StatusCode
import org.oreplay.app.model.data.Runner
import kotlin.time.Duration

@Composable
fun TableContentAccumulated(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    listState: LazyListState,
    data: List<Runner>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        state = listState,
    ) {
        items(data) { row ->
            // Runner information. SHOULDN'T Scroll
            Row(){
                if(row.result.position != 0L){
                    if(row.isNC) {
                        Text(
                            text = "NC",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else{
                        Text(
                            text = row.result.position.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onSurface

                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(row.fullName)
                    Text(row.club.shortName)
                }
            }
            Row (
                modifier = Modifier
                    .horizontalScroll(scrollState)
            ) {
                var item = row.splits.first
                // Total time
                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                        .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .padding(8.dp),

                    ) {
                    if(row.status == StatusCode.OK){
                        Text(
                            text = row.result.timeSeconds.toComponents { hrs, min, sec, _ ->
                                if (hrs > 0) {
                                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                } else {
                                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurface,
                            )
                        Text(
                            text = row.result.timeBehind.toComponents { hrs, min, sec, _ ->
                                if (hrs > 0) {
                                    "+${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                } else {
                                    "+${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurface,
                            )
                    }
                    else{
                        Text(
                            text = row.status.toString(),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                }

                var nextItem: ControlItem?
                var modifier: Modifier = Modifier
                    .width(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(8.dp)


                while(item != null){
                    nextItem = item.next

                    if(nextItem == null){
                        modifier = Modifier
                            .width(100.dp)
                            .padding(0.dp, 0.dp, 8.dp, 0.dp)
                            .clip(RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .padding(8.dp)

                    }
                    Column(
                        modifier = modifier,
                    ) {
                        if(item.accumulatedTime == Duration.INFINITE){
                            Text(
                                text = "--"
                            )
                        }
                        else if(item.isAccumulatedError){
                            Text(
                                text = item.accumulatedTime.toComponents { hrs, min, sec, _ ->
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
                                text = item.accumulatedTime.toComponents { hrs, min, sec, _ ->
                                    if (hrs > 0) {
                                        "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    } else {
                                        "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    }
                                }
                                        + " (" + item.accumulatedPosition + ")"
                            )
                        }

                        if(item.accumulatedTimeBehind == Duration.INFINITE){
                            Text(
                                text = "--"
                            )
                        }
                        else if(!item.isAccumulatedError){
                            Text(
                                text = "+" + item.accumulatedTimeBehind.toComponents { hrs, min, sec, _ ->
                                    if (hrs > 0) {
                                        "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    } else {
                                        "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                                    }
                                }
                            )
                        }
                    }
                    item = item.next
                }
            }
        }
    }
}