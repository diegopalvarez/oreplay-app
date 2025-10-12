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

class ClubResultsScreenComponent(
    val stage: Stage,
    val club: Club,
    val client: EventClient,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }

    private val _clubRunnerList = MutableStateFlow<List<Runner>>(emptyList())
    val clubRunnerList = _clubRunnerList.asStateFlow()

    private val _ticketRunner = MutableStateFlow<Runner?>(null)
    val ticketRunner = _ticketRunner.asStateFlow()

    // Scope
    private val scope = coroutineScope();

    fun getClubRunners() {
        scope.launch {
            client.getClubResults(stage, club)
                .onSuccess { data ->
                    _clubRunnerList.value = createRunners(data)
                }
                .onError {
                    println("Error fetching club results: $it")
                }
        }
    }

    fun getRunnerResults(runner: Runner) {
        scope.launch {
            client.getResults(stage, runner.runnerClass)
                .onSuccess { data ->
                    var runnerList = createRunners(data)
                    _ticketRunner.value = runnerList[runnerList.indexOfFirst { r -> r.id == runner.id }]
                }
                .onError {
                    println("Error fetching runner details for ticket in club: $it")
                }
        }
    }
}