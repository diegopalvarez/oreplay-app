package org.oreplay.app.view.stages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.Stage
import org.oreplay.app.viewmodel.stages.EventScreenComponent
import org.oreplay.app.viewmodel.stages.EventScreenEvent

@Composable
fun StageItem(
    component: EventScreenComponent,
    item: Stage
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp)
            .clickable(onClick = {
                component.onEvent(EventScreenEvent.ClickEvent, item)
            }),


    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = item.description,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = item.stageType.description,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Right
            )
        }
    }
}