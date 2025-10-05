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
    var SICard: String?
) {
    constructor(runner: RunnerResult) : this(
        fullName = runner.fullName,
        id = runner.id,
        club = runner.club,
        runnerClass = runner.runnerClass,
        startTime = runner.results.startTime,
        result = ResultInformation(runner.results),
        status = parseStatusCode(runner.results.statusCode),
        splits = ControlList(runner.results.startTime, runner.runnerClass.id,  runner.results.splits),
        SICard = runner.sicard
    )
}

fun createRunners(list: List<RunnerResult>): List<Runner> {
    val runnerList: ArrayList<Runner> = arrayListOf()

    for(runner in list) {
        runnerList.add(Runner(runner))
    }
    print(list.size.toString() + ", " + runnerList.size)
    // TODO - Threads
    calculateRunnerPositions(runnerList)
    return runnerList.sortedWith(compareBy({it.result.position == 0L}, { it.status }, { it.result.position }))
}