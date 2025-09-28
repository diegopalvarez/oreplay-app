package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
data class PageLink (
    val self: Link,
    val next: Link
){
}