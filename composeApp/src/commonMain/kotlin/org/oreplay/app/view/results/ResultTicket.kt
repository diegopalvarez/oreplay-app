package org.oreplay.app.view.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.controls.ControlItem
import org.oreplay.app.model.data.Runner

@Composable
fun ResultTicket(
    runner: Runner,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ){
        var item: ControlItem? = runner.splits.first

        while(item != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text(item.stationNumber.toString(), modifier = Modifier.padding(bottom = 8.dp))
            }
            item = item.next
        }
    }
}