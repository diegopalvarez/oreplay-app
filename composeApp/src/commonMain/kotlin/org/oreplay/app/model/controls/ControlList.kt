package org.oreplay.app.model.controls

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.Split
import org.oreplay.app.model.Stage
import org.oreplay.app.model.data.Runner
import org.oreplay.app.model.data.createRunners
import org.oreplay.app.model.util.onSuccess
import kotlin.time.Duration
import kotlin.time.Instant

class ControlList(
    val startTime: Instant? = null,
    val classID: String,
) {
    // Attributes
    var size: Int = 0
    var first: ControlItem? = null
    var last: ControlItem? = null

    // Incorrect Controls
    // TODO - Know how they store controls not in the course
    var sizeIncorrect: Int = 0
    var firstIncorrect: ControlItem? = null
    var lastIncorrect: ControlItem? = null

    var isSplitTimeCalculated = false

    // TODO - Optimize
    constructor(startTime: Instant, finishTime: Instant?, classID: String, splits: List<Split>): this(startTime, classID) {
        val controlGroups  = splits.groupBy { it.orderNumber }

        val correctControls = ArrayList<Split>()
        val incorrectControls = ArrayList<Split>()

        for((_, items) in controlGroups) {
            // Get the latest punch in a group, store the others
            val best = items
                .filter { it.readingTime != null }
                .maxByOrNull { it.readingTime!! }
                ?: items.first()

            correctControls.add(best)
            items.filter { it != best }.forEach {i -> incorrectControls.add(i) }
        }

        // TODO - SortBy does it in place
        var orderedControls = correctControls.sortedBy{ it.orderNumber }
        var incorrectOrderedControls = incorrectControls.sortedBy{ it.readingTime?: Instant.DISTANT_FUTURE }

        // TODO - Can it be optimized
        var errorHappened = false
        for(control in orderedControls){
            if(errorHappened || control.readingTime == null){
                errorHappened = true
                add(control, true)
            }
            else {
                add(control)
            }
        }

        // Add finish line control
        addFinish(finishTime)

        for(control in incorrectOrderedControls){
            addIncorrect(control)
        }

    }

    constructor(classID: String, splits: List<Split>): this(null, classID) {
        // All splits are incorrect.
        var orderedControls = splits.sortedBy{ it.orderNumber }

        for(control in orderedControls){
            add(control)
        }

        // Add finish control
        addFinish(null)
    }

    // Functions
    fun first(): ControlItem? = first
    fun size(): Int = size

    fun add(control: Split){
        if(size == 0){
            first = ControlItem(control)
            last = first
            size++;
        }
        else if(size > 0){
            val new = ControlItem(control)
            last?.next = new
            last = new
            size++;
        }
        else{
            // TODO - Errors
            print("ERROR")
        }

    }

    fun add(control: Split, error: Boolean){
        if(size == 0){
            first = ControlItem(control, error)
            last = first
            size++;
        }
        else if(size > 0){
            val new = ControlItem(control, error)
            last?.next = new
            last = new
            size++;
        }
        else{
            // TODO - Errors
            print("ERROR")
        }

    }

    fun addIncorrect(control: Split){
        if(sizeIncorrect == 0){
            firstIncorrect = ControlItem(control)
            lastIncorrect = firstIncorrect
            sizeIncorrect++;
        }
        else if(sizeIncorrect > 0){
            val new = ControlItem(control)
            lastIncorrect?.next = new
            lastIncorrect = new
            sizeIncorrect++;
        }
        else{
            // TODO - Errors
            print("ERROR")
        }

    }

    fun addFinish(finishTime: Instant?){
        if(size == 0){
            first = ControlItem(finishTime)
            last = first
            size++;
        }
        else if(size > 0){
            val new = ControlItem(finishTime)
            last?.next = new
            last = new
            size++;
        }
        else{
            // TODO - Errors
            print("ERROR")
        }
    }

    fun getSplitTimes(): List<Duration> {
        val times = ArrayList<Duration>()
        if (!isSplitTimeCalculated){
            calculateSplitTimes()
        }

        var control = first

        while(control != null){
            times.add(control.timeBehind)
            control = control.next
        }

        return times
    }

    // TODO - Handle repeated reads of same control
    // TODO - Create version without return
    fun calculateSplitTimes(){
        var control = first
        var previous: ControlItem? = null

        if(startTime == null){
            return
        }

        // TODO - Optimize
        while(control != null){
            if(control.readingTime == null){
                control.splitTime = Duration.INFINITE
                control.accumulatedTime = Duration.INFINITE
            }
            else{
                control.accumulatedTime = Duration.parse((control.readingTime - startTime).toString())
                if(previous == null){
                    // First control
                    control.splitTime = control.accumulatedTime
                }
                else if(previous.readingTime == null){
                    control.splitTime = Duration.INFINITE
                }
                else{
                    control.splitTime = Duration.parse((control.readingTime - previous.readingTime).toString())
                }
            }
            previous = control
            control = control.next
        }

    }

    // Operators
    // TODO - Optimize
    operator fun get(index: Int) : ControlItem{
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index $index out of bounds for size $size")
        }

        var item = first

        repeat(index) {
            item = item?.next
        }

        return item ?: throw IllegalStateException("Linked list structure broken at index $index")
    }
}

// TODO - Question: What happens with overpunched controls?
fun calculatePositions(list: List<ControlList>) {
    if(list.isEmpty()) return

    val course = list.first().classID
    list.forEach { it.getSplitTimes() }
    // Check courses are compatible for comparison, and calculate split times
    if (list.any { it.classID != course }) {
        println("ERROR: incompatible courses")
        return
    }

    val controlNumber = list.first().size

    for (control in 0 until controlNumber) {
        // First step for getting the best time

        // Get the current control for every runner
        val runnerPerControlList = list.map { it[control] }

        // Order the controls by split time and get the best time
        val orderedTimesList: List<ControlItem> = runnerPerControlList.sortedBy { it.splitTime }
        val bestTime = orderedTimesList.first().splitTime

        // TODO - Note: In accumulated times, the positions stops being calculated after first mistake
        val orderedAccumulatedTimesList: List<ControlItem> = orderedTimesList.filter { !it.isAccumulatedError }.sortedBy { it.accumulatedTime }
        val bestAccumulatedTime = orderedAccumulatedTimesList.first().accumulatedTime


        // Second step for time difference and positions
        var lastPosition = 0;
        for ((index, controlObject) in orderedTimesList.withIndex()) {
            if (controlObject.splitTime == Duration.INFINITE) {
                controlObject.position = 0
            } else {
                // Handle ties in every position. The list is ordered, so it's only possible in consecutive results
                if(index > 0 && controlObject.splitTime == orderedTimesList[index - 1].splitTime){
                    controlObject.position = orderedTimesList[index - 1].position
                    lastPosition++;
                }
                else{
                    controlObject.position = ++lastPosition
                }
                controlObject.timeBehind = controlObject.splitTime - bestTime
            }
        }

        // TODO - Modularize and parallelize
        // Same for accumulated times
        lastPosition = 0;
        for ((index, controlObject) in orderedAccumulatedTimesList.withIndex()) {
            if (controlObject.accumulatedTime == Duration.INFINITE) {
                controlObject.accumulatedPosition = 0
            } else {
                // Handle ties in every position. The list is ordered, so it's only possible in consecutive results
                if(index > 0 && controlObject.accumulatedTime == orderedAccumulatedTimesList[index - 1].accumulatedTime){
                    controlObject.accumulatedPosition = orderedAccumulatedTimesList[index - 1].accumulatedPosition
                    lastPosition++;
                }
                else{
                    controlObject.accumulatedPosition = ++lastPosition
                }
                controlObject.accumulatedTimeBehind = controlObject.accumulatedTime - bestAccumulatedTime
            }
        }
    }
}

fun calculateRunnerPositions(list: List<Runner>){
    // TODO - Note: In case of walkover? no finish time shows up even though results appear
    // TODO - Fix: This avoids reading undownloaded splits, but poorly
    val lista: List<ControlList?> = list.filter { it.status != StatusCode.OK || it.result.finishTime != null } .map { it.splits }
    calculatePositions(lista.filterNotNull())
}

suspend fun calculateClubRunnerPositions(list: List<Runner>, stage: Stage, client: EventClient): ArrayList<Runner>{
    // Declare resulting list
    val resultList: ArrayList<Runner> = arrayListOf()

    // Group in classes for optimization
    val runnerGroupsPerClass = list.groupBy { it.runnerClass }

    // Get all Runners from each class
    for(runners in runnerGroupsPerClass){
        // Get results from API
        var results: List<RunnerResult> = listOf()
        client.getResults(stage, runners.key)
            .onSuccess {
                results = it
            }

        val runnerList = createRunners(results)
        val lista: List<ControlList?> = runnerList.filter { it.status != StatusCode.OK || it.result.finishTime != null } .map { it.splits }
        calculatePositions(lista.filterNotNull())

        resultList.addAll(runnerList.filter { classRunner -> classRunner.id in runners.value.map { clubRunner -> clubRunner.id } })
    }

    return resultList
}