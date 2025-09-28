package org.oreplay.app.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class EventDetailResponse (
    val data : EventDetails,
) {

}