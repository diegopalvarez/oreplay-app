package org.oreplay.app.model.controls

enum class StatusCode(val code: Int, val order: Int) {
    OK(0, 0),
    DNS(1, 6),
    DNF(2, 4),
    MP(3, 1),
    DISQUALIFIED(4, 3),
    OVERTIME(5, 2),
    ERROR(-1, 6);
}

// TODO - I got a 9 and I don't know what it means

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