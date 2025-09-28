package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDetails(
    @SerialName("stages")
    val stages: List<Stage>,
    val federation: String? = null,
){

}