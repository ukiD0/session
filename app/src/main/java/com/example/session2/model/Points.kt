package com.example.session2.model

import kotlinx.serialization.Serializable

@Serializable
data class Points(
    var id: String? = null,
    var created_at: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var index: Int? = null,
    var pin_image: String? = null
)
