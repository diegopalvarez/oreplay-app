package org.oreplay.app.viewmodel

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onNavigateToEventScreen: (Event) -> Unit
): ComponentContext by componentContext {

    private var _text = MutableValue("")
    val text: Value<String> = _text

    fun onEvent(event: HomeScreenEvent, raceEvent: Event) {
        when(event) {
            HomeScreenEvent.ClickEvent -> onNavigateToEventScreen(raceEvent)
        }
    }
}