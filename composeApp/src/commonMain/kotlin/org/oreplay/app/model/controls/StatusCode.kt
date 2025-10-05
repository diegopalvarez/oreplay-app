package org.oreplay.app.model.controls

enum class StatusCode(val code: Int) {
    OK(0),
    DNS(1),
    DNF(2),
    MP(3),
    DISQUALIFIED(4),
    OVERTIME(5),
    ERROR(-1);
}

fun parseStatusCode(statusCode: Int): StatusCode {
    return when (statusCode) {
        0 -> StatusCode.OK
        1 -> StatusCode.DNS
        2 -> StatusCode.DNF
        3 -> StatusCode.MP
        4 -> StatusCode.DISQUALIFIED
        5 -> StatusCode.OVERTIME
        else -> StatusCode.ERROR
    }
}