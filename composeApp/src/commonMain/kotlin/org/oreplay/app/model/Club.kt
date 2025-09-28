package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Club (
    val id: String,

    @SerialName("short_name")
    val shortName: String
){
}