package org.oreplay.app.viewmodel

import com.arkivanov.decompose.ComponentContext
import org.oreplay.app.model.Class
import org.oreplay.app.model.Club
import org.oreplay.app.model.Event
import org.oreplay.app.model.Stage

class ClubResultsScreenComponent(
    val stage: Stage,
    val club: Club,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }
}