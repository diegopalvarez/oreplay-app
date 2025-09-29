package org.oreplay.app.view.results

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

@Composable
fun TableHeader (
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    header: List<String>
    ){
    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .background(Color.Gray)
            .padding(8.dp)
    ) {
        header.forEach { headerItem->
            Text(
                text = headerItem,
                modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )

        }
    }
}