package org.oreplay.app.model

import kotlinx.serialization.Serializable

@Serializable
class ClassResponse (
    val data: List<Class>
){}