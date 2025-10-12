package org.oreplay.app.view.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.oreplay.app.model.Event
import org.oreplay.app.viewmodel.home.HomeScreenComponent
import org.oreplay.app.viewmodel.home.HomeScreenEvent

@Composable
fun EventBox(
    component: HomeScreenComponent,
    item: Event
) {
    Box(
        modifier = Modifier
            .height(170.dp)
            .width(170.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp)
            .clickable(onClick = {
                component.onEvent(HomeScreenEvent.ClickEvent, item)
            }),


    ) {
        Text(
            text = item.description,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        var dates = "";

        if(item.initialDate != item.finalDate) {
            dates = item.initialDate.toString() + " - " + item.finalDate.toString();
        }
        else{
            dates = item.initialDate.toString();
        }

        Text(
            text = dates,
            style = MaterialTheme.typography.labelSmall,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}