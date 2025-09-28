package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
data class StageLinks (
    val self: String,
    val results: String,
    val classes: String
) {}