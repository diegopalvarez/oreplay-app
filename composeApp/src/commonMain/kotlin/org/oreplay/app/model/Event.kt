package org.oreplay.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event (
    val id: String,
    @SerialName("is_hidden")
    val isHidden: Boolean,
    val description: String,
    val picture: String? = null,
    val website: String? = null,
    val scope: String,
    val location: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    @SerialName("initial_date")
    val initialDate: String,
    @SerialName("final_date")
    val finalDate: String,
    @SerialName("federation_id")
    val federationId: String? = null,
    val created: String,
    val modified: String,
    @SerialName("organizer_id")
    val organizerId: String,
    val organizer: Organizer,
    @SerialName("_links")
    val links: EventLink
){

}