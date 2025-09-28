package org.oreplay.app.viewmodel

sealed interface ClassScreenEvent {
    data object ClickEvent : ClassScreenEvent
}