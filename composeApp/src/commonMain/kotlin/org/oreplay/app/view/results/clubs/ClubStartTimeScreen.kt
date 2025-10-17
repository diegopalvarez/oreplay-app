package org.oreplay.app.view.results.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.oreplay.app.model.data.Runner
import org.oreplay.app.viewmodel.results.ClubResultsScreenComponent

@Composable
fun StartTimeScreen(
    component: ClubResultsScreenComponent,
    contentPadding: PaddingValues,
) {
    var errorMessage by remember {
        mutableStateOf<String>("No error")
    }

    val runnerList by component.clubRunnerList.collectAsState()
    component.getClubRunners();

    val results = runnerList.sortedWith(compareBy(nullsLast()) { it.startTime })

    LazyColumn(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        items(results) { runner ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),

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