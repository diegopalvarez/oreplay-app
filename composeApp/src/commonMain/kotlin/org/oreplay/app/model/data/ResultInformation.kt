package org.oreplay.app.model.data

import org.oreplay.app.model.ResultDetails
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.Instant
import kotlin.time.toDuration

class ResultInformation(
    var timeSeconds: Duration,
    var finishTime: Instant?,
    var position: Long,
    var isNc: Boolean,
    var timeBehind: Duration,
    var points: String
) {
    constructor(result: ResultDetails): this(result.timeSeconds.toDuration(DurationUnit.SECONDS), result.finishTime, result.position, result.isNc, result.timeBehind.toDuration(DurationUnit.SECONDS), result.pointsFinal)
}