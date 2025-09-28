package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stage (
    val id: String,
    val description: String,

    @SerialName("stage_type")
    val stageType: StageType,

    @SerialName("last_logs")
    val lastLogs: List<LastLog>,

    @SerialName("_links")
    val links: StageLinks
) {}