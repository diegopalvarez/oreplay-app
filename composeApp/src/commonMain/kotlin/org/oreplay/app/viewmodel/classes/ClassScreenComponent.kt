package org.oreplay.app.viewmodel.classes

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.oreplay.app.model.Class
import org.oreplay.app.model.Club
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.Stage
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess

class ClassScreenComponent(
    val stage: Stage,
    componentContext: ComponentContext,
    val client: EventClient,
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

    private val _classList = MutableStateFlow<List<Class>>(emptyList())
    val classList = _classList.asStateFlow()

    private val _clubList = MutableStateFlow<List<Club>>(emptyList())
    val clubList = _clubList.asStateFlow()

    // Scope
    private val scope = coroutineScope();

    fun getStageClasses() {
        scope.launch {
            client.getClasses(stage)
                .onSuccess { classes ->
                    _classList.value = classes
                        .sortedBy { it.shortName }
                }
                .onError {
                    println("Error fetching stage classes: $it")
                }
        }
    }

    fun getStageClubs() {
        scope.launch {
            client.getClubs(stage)
                .onSuccess { clubs ->
                    _clubList.value = clubs
                        .sortedBy { it.shortName }
                }
                .onError {
                    println("Error fetching stage clubs: $it")
                }
        }
    }
}