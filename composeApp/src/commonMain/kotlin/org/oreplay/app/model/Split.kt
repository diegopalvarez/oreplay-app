package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Split (
    val id: String,

    @SerialName("is_intermediate")
    val isIntermediate: Boolean,    // TODO - WTF?

    @SerialName("reading_time")
    val readingTime: String?,

    val points: Long,

    @SerialName("order_number")
    val orderNumber: Long,

    val created: String,
    val control: Control
){
}