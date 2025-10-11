package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Overall (
    val id: String,

    @SerialName("stage_order")
    val stageOrder: Long,

    @SerialName("upload_type")
    val uploadType: String,

    val stage: Stage? = null,
    val position: Long,

    @SerialName("status_code")
    val statusCode: String,

    @SerialName("is_nc")
    val isNc: Boolean,

    val contributory: Boolean? = null,

    @SerialName("time_seconds")
    val timeSeconds: Long,

    @SerialName("time_behind")
    val timeBehind: Long,

    @SerialName("points_final")
    val pointsFinal: Long,

    val note: String? = null
)