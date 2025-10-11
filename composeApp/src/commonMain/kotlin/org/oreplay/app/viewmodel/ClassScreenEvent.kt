package org.oreplay.app.viewmodel

sealed interface ClassScreenEvent {
    data object ClickClassEvent : ClassScreenEvent
    data object ClickClubEvent : ClassScreenEvent
}