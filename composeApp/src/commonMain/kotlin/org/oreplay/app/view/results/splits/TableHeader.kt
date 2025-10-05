package org.oreplay.app.view.results.splits

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.controls.ControlList

@Composable
fun TableHeader (
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    header: ControlList
    ){
    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .background(Color.Gray)
            .padding(8.dp)
    ) {
        var item = header.first()
        var index = 0;
        // Total time
        Text(
            text = "Time",
            modifier = Modifier
                .width(100.dp)
                .padding(8.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )


        while(item != null){
            if(item.isFinishControl) {
                Text(
                    text = "Fin",
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
            else{
                Text(
                    text = (++index).toString() + " (" + item.stationNumber + ")",
                    modifier = Modifier
                        .width(100.dp)
                        .padding(8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
            item = item.next
        }
    }
}