package com.example.session2.model

import kotlinx.serialization.Serializable

@Serializable
data class destinations_details(
    var id: String? = null,
    var id_order: String?,
    var address: String? = null,
    var country: String? = null,
    var phone: String? = null,
    var others: String? = null,
    var created_at: String? = null
)
