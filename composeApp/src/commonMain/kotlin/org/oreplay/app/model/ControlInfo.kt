package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
class ControlInfo(
    val id: String,
    val description: String
) {
}