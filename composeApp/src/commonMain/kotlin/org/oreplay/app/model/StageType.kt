package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
data class StageType (
    val id: String,
    val description: String
)