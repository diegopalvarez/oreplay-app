package org.oreplay.app.view.results.clubs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.oreplay.app.model.EventClient
import org.oreplay.app.viewmodel.ClubResultsScreenComponent

@Composable
fun ClubResultsScreen(
    component: ClubResultsScreenComponent,
    client: EventClient
) {
    Text(text = "Club Results Screen")
}