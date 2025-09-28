package org.oreplay.app.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class EventResponse (
    val data : List<Event>,
    val total: Long,
    val limit: Long,

    @SerialName("_links")
    val links : PageLink
) {

}