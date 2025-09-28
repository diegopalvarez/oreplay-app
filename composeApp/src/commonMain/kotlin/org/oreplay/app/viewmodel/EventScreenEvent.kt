package org.oreplay.app.viewmodel

sealed interface EventScreenEvent {
    data object ClickEvent : EventScreenEvent
}