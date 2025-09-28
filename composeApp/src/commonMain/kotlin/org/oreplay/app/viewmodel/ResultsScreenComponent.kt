package org.oreplay.app.viewmodel

import com.arkivanov.decompose.ComponentContext
import org.oreplay.app.model.Class
import org.oreplay.app.model.Event
import org.oreplay.app.model.Stage

class ResultsScreenComponent(
    val stage: Stage,
    val raceClass: Class,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }
}