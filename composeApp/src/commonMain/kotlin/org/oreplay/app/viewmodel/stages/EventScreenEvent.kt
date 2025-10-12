package org.oreplay.app.viewmodel.stages

sealed interface EventScreenEvent {
    data object ClickEvent : EventScreenEvent
}