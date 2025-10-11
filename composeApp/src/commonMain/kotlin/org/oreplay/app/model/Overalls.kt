package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Overalls (
    val parts: List<Overall>,
    val overall: Overall
)