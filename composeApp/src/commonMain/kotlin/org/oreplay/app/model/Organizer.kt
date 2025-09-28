package org.oreplay.app.model

import kotlinx.serialization.Serializable
@Serializable
data class Organizer (
    val id: String,
    val name: String,
    val country: String,
    val region: String? = null
){
}