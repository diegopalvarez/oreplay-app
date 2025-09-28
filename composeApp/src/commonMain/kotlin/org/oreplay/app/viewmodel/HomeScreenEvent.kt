package org.oreplay.app.viewmodel

sealed interface HomeScreenEvent {
    data object ClickEvent : HomeScreenEvent
}