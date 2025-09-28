package org.oreplay.app.viewmodel

import com.arkivanov.decompose.ComponentContext
import org.oreplay.app.model.Class
import org.oreplay.app.model.Event
import org.oreplay.app.model.Stage
import org.oreplay.app.view.ClassScreen

class ClassScreenComponent(
    val stage: Stage,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit,
    private val onNavigateToResultsScreen: (Stage, Class) -> Unit
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }

    fun onEvent(event: ClassScreenEvent, stage: Stage, raceClass: Class) {
        when(event) {
            ClassScreenEvent.ClickEvent -> onNavigateToResultsScreen(stage, raceClass)
        }
    }
}