package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Class (
    val id: String,
    @SerialName("short_name")
    val shortName: String,

    @SerialName("long_name")
    val longName: String,

    //TODO - Ignoring Splits
    // When adding splits, have in mind a new class will be needed for RunnerResults (no splits returned)
)