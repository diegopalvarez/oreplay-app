package org.oreplay.app.model.controls

import org.oreplay.app.model.Split
import kotlin.time.Duration
import kotlin.time.Instant

class ControlItem(
    val stationNumber: Long,
    val readingTime: Instant?,
    val points: Long,
    var isFinishControl:Boolean = false
) {
    // Attributes
    var position: Int = -1
    var accumulatedPosition = -1;
    var splitTime: Duration = Duration.INFINITE
    var accumulatedTime: Duration = Duration.INFINITE
    var timeBehind: Duration = Duration.INFINITE
    var accumulatedTimeBehind: Duration = Duration.INFINITE
    var isAccumulatedError = false

    var next: ControlItem? = null;

    constructor(split: Split) : this(split.control.station, split.readingTime, split.points){}
    constructor(split: Split, error: Boolean) : this(split.control.station, split.readingTime, split.points){
        isAccumulatedError = error
    }

    // TODO - Check usage
    constructor() : this(-1, null, -1)
    constructor(finishTime: Instant?) : this(-1, finishTime, -1, true) {}
}