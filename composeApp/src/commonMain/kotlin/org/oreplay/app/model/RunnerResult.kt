package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RunnerResult (
    val id: String,

    @SerialName("bib_number")
    val bibNumber: String?,

    @SerialName("is_nc")
    val isNc: Boolean,

    // TODO - val eligibility: JsonElement? = null,
    // TODO - sicard can be null but they don't say it because they don't FUCKING CARE!!!
    val sicard: String?,
    // TODO - Check how it's created: enum or string?
    val sex: String,

    @SerialName("leg_number")
    val legNumber: Long,

    val created: String,

    @SerialName("class")
    val runnerClass: Class,

    val club: Club,

    @SerialName("full_name")
    val fullName: String,

    @SerialName("stage")
    val results: ResultDetails,
    // TODO - val overalls: JsonElement? = null
){
}