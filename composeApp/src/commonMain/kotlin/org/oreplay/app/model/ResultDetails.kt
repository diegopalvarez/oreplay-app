package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
class ResultDetails (
    val id: String,

    @SerialName("result_type_id")
    val resultTypeID: String,

    @SerialName("start_time")
    val startTime: Instant?,

    @SerialName("finish_time")
    val finishTime: Instant?,

    @SerialName("upload_type")
    val uploadType: String,

    @SerialName("time_seconds")
    val timeSeconds: Long,

    val position: Long,

    @SerialName("status_code")
    val statusCode: Int,

    @SerialName("is_nc")
    val isNc: Boolean,

    val contributory: Boolean?,

    @SerialName("time_behind")
    val timeBehind: Long,

    @SerialName("time_neutralization")
    val timeNeutralization: Long,

    @SerialName("time_adjusted")
    val timeAdjusted: Long,

    @SerialName("time_penalty")
    val timePenalty: Long,

    @SerialName("time_bonus")
    val timeBonus: Long,

    @SerialName("points_final")
    val pointsFinal: String,

    @SerialName("points_adjusted")
    val pointsAdjusted: String,

    @SerialName("points_penalty")
    val pointsPenalty: String,

    @SerialName("points_bonus")
    val pointsBonus: String,

    // TODO - val note: JsonElement? = null,

    @SerialName("leg_number")
    val legNumber: Long,

    val created: String,
    val splits: List<Split>
){
}