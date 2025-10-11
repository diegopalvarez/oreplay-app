package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
class ClubResponse (
    val data: List<Club>
){}