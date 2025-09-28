package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Control(
    val id: String,
    val station: String,

    @SerialName("control_type")
    val controlType: ControlInfo
) {
}