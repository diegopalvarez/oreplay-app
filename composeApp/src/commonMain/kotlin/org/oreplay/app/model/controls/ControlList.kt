package org.oreplay.app.model.controls

import org.oreplay.app.model.Split
import org.oreplay.app.model.data.Runner
import kotlin.time.Duration
import kotlin.time.Instant

class ControlList(
    val startTime: Instant,
    val classID: String,
) {
    // Attributes
    var size: Int = 0
    var first: ControlItem? = null
    var last: ControlItem? = null

    var isSplitTimeCalculated = false

    constructor(startTime: Instant, classID: String, splits: List<Split>): this(startTime, classID) {
        val controls = splits.sortedBy { it.orderNumber }
        for(control in controls){
            add(control)
        }
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

    // TODO - Create version without return
    fun calculateSplitTimes(){
        var control = first
        var previous: ControlItem? = null

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
        println("Control: " + control + " - " + list.first()[control].stationNumber)

        // Get the current control for every runner
        val runnerPerControlList = list.map { it[control] }

        // Order the controls by split time and get the best time
        val orderedTimesList: List<ControlItem> = runnerPerControlList.sortedBy { it.splitTime }
        val bestTime = orderedTimesList.first().splitTime


        // Second step for time difference and positions
        var lastPosition = 0;
        for ((index, controlObject) in orderedTimesList.withIndex()) {
            if (controlObject.splitTime == Duration.INFINITE) {
                controlObject.position = 0
            } else {
                // Handle ties in every position. The list is ordered, so it's only possible in consecutive results
                if(index > 0 && controlObject.splitTime == orderedTimesList[index - 1].splitTime){
                    controlObject.position = orderedTimesList[index - 1].position
                }
                else{
                    controlObject.position = ++lastPosition
                }
                controlObject.timeBehind = controlObject.splitTime - bestTime
            }
        }
    }
}

fun calculateRunnerPositions(list: List<Runner>){
    calculatePositions(list.filter { it.result.finishTime != null } .map { it.splits })
}