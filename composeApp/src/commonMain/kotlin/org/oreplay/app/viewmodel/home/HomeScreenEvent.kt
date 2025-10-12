package org.oreplay.app.viewmodel.home

sealed interface HomeScreenEvent {
    data object ClickEvent : HomeScreenEvent
}