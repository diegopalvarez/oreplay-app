package org.oreplay.app.viewmodel.results

import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.oreplay.app.model.Class
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.Stage
import org.oreplay.app.model.controls.ControlItem
import org.oreplay.app.model.data.Runner
import org.oreplay.app.model.data.createRunners
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import kotlin.time.Duration

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

    private val _cellWidth = MutableStateFlow<Dp>(100.dp)
    val cellWidth = _cellWidth.asStateFlow()

    private fun measureCellWidth(
        control: ControlItem,
        textMeasurer: TextMeasurer,
        density: Density,
        textStyle: TextStyle
    ){
        var text = ""
        var maxWidth = 0
        var width = 0

        if (control.splitTime == Duration.INFINITE) {
            text = "--"
        } else {
            text = control.splitTime.toComponents { hrs, min, sec, _ ->
                if (hrs > 0) {
                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                } else {
                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                }
            } + " (" + control.position + ")"
        }

        maxWidth = textMeasurer.measure(text, textStyle).size.width

        if (control.timeBehind == Duration.INFINITE) {
            text = "--"
        } else {
            text = "+" + control.timeBehind.toComponents { hrs, min, sec, _ ->
                if (hrs > 0) {
                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                } else {
                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                }
            }
        }

        width = textMeasurer.measure(text).size.width
        if (width > maxWidth) {
           maxWidth = width
        }

        if (control.accumulatedTime == Duration.INFINITE) {
            text = "--"
        } else if (control.isAccumulatedError) {

            text = control.accumulatedTime.toComponents { hrs, min, sec, _ ->
                if (hrs > 0) {
                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                } else {
                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                }
            }
        } else {
            text = control.accumulatedTime.toComponents { hrs, min, sec, _ ->
                if (hrs > 0) {
                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                } else {
                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                }
            } + " (" + control.accumulatedPosition + ")"
        }

        width = textMeasurer.measure(text, textStyle).size.width
        if (width > maxWidth) {
            maxWidth = width
        }



        if (control.accumulatedTimeBehind == Duration.INFINITE) {
            text = "--"
        } else if (!control.isAccumulatedError) {
            text = "+" + control.accumulatedTimeBehind.toComponents { hrs, min, sec, _ ->
                if (hrs > 0) {
                    "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                } else {
                    "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                }
            }
        }

        width = textMeasurer.measure(text, textStyle).size.width
        if (width > maxWidth) {
            maxWidth = width
        }
        val finalWidth = with(density) { maxWidth.toDp() } + 32.dp // padding

        if(finalWidth > _cellWidth.value) {
            _cellWidth.value = finalWidth
        }
    }

    fun getCellWidth(
        textMeasurer: TextMeasurer,
        density: Density,
        textStyle: TextStyle
    ) {
        scope.launch {
            runnerList.value.forEachIndexed { rowIndex, row ->
                var control = row.splits.first
                while (control != null) {
                    measureCellWidth(control, textMeasurer, density, textStyle)
                    control = control.next
                }
            }
        }
    }
}