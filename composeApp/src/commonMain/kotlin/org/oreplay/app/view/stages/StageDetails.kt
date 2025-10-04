package org.oreplay.app.view.stages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.Event

@Composable
fun StageDetails(event: Event) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        // TODO - Image: What's the format?
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = event.description
            )
            Text(
                text = event.organizer.name
            )

            event.location?.let { location ->
                Text(
                    text = location
                )
            }
        }

    }
}