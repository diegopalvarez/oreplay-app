package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
data class LastLog(
    val state: Long,
    val created: String
) {}