package org.oreplay.app.viewmodel.stages

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.oreplay.app.model.Club
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.EventDetails
import org.oreplay.app.model.Stage
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess

class EventScreenComponent(
    val event: Event,
    val client: EventClient,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit,
    private val onNavigateToClassScreen: (Stage) -> Unit
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }

    fun onEvent(event: EventScreenEvent, stage: Stage) {
        when(event) {
            EventScreenEvent.ClickEvent -> {
                when(stage.stageType.description) {
                    "Classic" -> onNavigateToClassScreen(stage)
                    "Relay" -> println("Relays not supported")
                    "Overall" -> println("Overall not supported")
                    else -> println("Unknown stage: $stage")
                }
            }
        }
    }

    private val _eventDetails = MutableStateFlow<EventDetails?>(null)
    val eventDetails = _eventDetails.asStateFlow()

    // Scope
    private val scope = coroutineScope();

    fun getEventDetails() {
        scope.launch {
            client.getEventDetails(event)
                .onSuccess { details ->
                    _eventDetails.value = details
                }
                .onError {
                    println("Error fetching event details: $it")
                }
        }
    }
}