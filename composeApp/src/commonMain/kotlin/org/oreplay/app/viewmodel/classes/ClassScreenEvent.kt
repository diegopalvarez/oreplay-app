package org.oreplay.app.viewmodel.classes

sealed interface ClassScreenEvent {
    data object ClickClassEvent : ClassScreenEvent
    data object ClickClubEvent : ClassScreenEvent
}