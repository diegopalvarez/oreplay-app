package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
class ResultsResponse (
    val data: List<RunnerResult>
){
}