package org.oreplay.app.viewmodel.results

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.oreplay.app.model.Class
import org.oreplay.app.model.Club
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.Stage
import org.oreplay.app.model.data.Runner
import org.oreplay.app.model.data.createRunners
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess

class ResultsScreenComponent(
    val stage: Stage,
    val raceClass: Class,
    val client: EventClient,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }

    private val _runnerList = MutableStateFlow<List<Runner>>(emptyList())
    val runnerList = _runnerList.asStateFlow()

    // Scope
    private val scope = coroutineScope();

    fun getRunners() {
        scope.launch {
            client.getResults(stage, raceClass)
                .onSuccess { data ->
                    _runnerList.value = createRunners(data)
                }
                .onError {
                    println("Error fetching class results: $it")
                }
        }
    }
}