package org.oreplay.app.viewmodel

import com.arkivanov.decompose.ComponentContext
import org.oreplay.app.model.Event
import org.oreplay.app.model.Stage

class EventScreenComponent(
    val event: Event,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit,
    private val onNavigateToClassScreen: (Stage) -> Unit
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }

    fun onEvent(event: EventScreenEvent, stage: Stage) {
        when(event) {
            EventScreenEvent.ClickEvent -> onNavigateToClassScreen(stage)
        }
    }
}