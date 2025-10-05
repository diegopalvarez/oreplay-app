package org.oreplay.app.model.controls

import org.oreplay.app.model.Split
import kotlin.time.Duration
import kotlin.time.Instant

class ControlItem(
    val stationNumber: Long,
    val readingTime: Instant?,
    val points: Long
) {
    // Attributes
    var position: Int = -1
    var splitTime: Duration = Duration.INFINITE
    var accumulatedTime: Duration = Duration.INFINITE
    var timeBehind: Duration = Duration.INFINITE
    var isTied: Boolean = false // TODO - Needed?
    var next: ControlItem? = null;

    constructor(split: Split) : this(split.control.station, split.readingTime, split.points){}
    // TODO - Check usage
    constructor() : this(-1, null, -1)

}