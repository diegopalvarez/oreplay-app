package org.oreplay.app.model.data

import org.oreplay.app.model.Class
import org.oreplay.app.model.Club
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.controls.ControlList
import org.oreplay.app.model.controls.StatusCode
import org.oreplay.app.model.controls.calculatePositions
import org.oreplay.app.model.controls.calculateRunnerPositions
import org.oreplay.app.model.controls.parseStatusCode
import kotlin.time.Instant

class Runner(
    val fullName: String,
    val id: String,
    val club: Club,
    val runnerClass: Class,
    val startTime: Instant,
    var result: ResultInformation,
    var status: StatusCode,
    var splits: ControlList,
    var SICard: String?,
    var isNC: Boolean
) {
    constructor(runner: RunnerResult) : this(
        fullName = runner.fullName,
        id = runner.id,
        club = runner.club,
        runnerClass = runner.runnerClass,
        startTime = runner.results.startTime,
        result = ResultInformation(runner.results),
        status = parseStatusCode(runner.results.statusCode),
        splits = ControlList(runner.results.startTime, runner.results.finishTime, runner.runnerClass.id,  runner.results.splits),
        SICard = runner.sicard,
        isNC = runner.isNc
    )
}

// TODO - Question: Can a NC have the best split?

fun createRunners(list: List<RunnerResult>): List<Runner> {
    val runnerList: ArrayList<Runner> = arrayListOf()

    for(runner in list) {
        runnerList.add(Runner(runner))
    }
    print(list.size.toString() + ", " + runnerList.size)
    // TODO - Threads
    calculateRunnerPositions(runnerList)
    // NC's are inserted in the list in their natural order, but displaying its condition
    return runnerList.sortedWith(compareBy({it.result.position == 0L},{ it.status }, { it.result.position }))
}