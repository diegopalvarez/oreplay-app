package org.oreplay.app.viewmodel

import com.arkivanov.decompose.ComponentContext
import io.ktor.util.reflect.instanceOf
import org.oreplay.app.model.Class
import org.oreplay.app.model.Club
import org.oreplay.app.model.Stage

class ClassScreenComponent(
    val stage: Stage,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit,
    private val onNavigateToResultsScreen: (Stage, Class) -> Unit,
    private val onNavigateToClubResultsScreen: (Stage, Club) -> Unit,
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }

    fun onClassEvent(event: ClassScreenEvent, stage: Stage, raceClass: Class) {
        if(event.instanceOf(ClassScreenEvent.ClickClassEvent::class)) {
            onNavigateToResultsScreen(stage, raceClass)
        }
    }

    fun onClubEvent(event: ClassScreenEvent, stage: Stage, club: Club) {
        if(event.instanceOf(ClassScreenEvent.ClickClubEvent::class)) {
            onNavigateToClubResultsScreen(stage, club)
        }
    }
}