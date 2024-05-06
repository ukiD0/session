package com.example.session2.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderStatus(
    var id: Int? = null,
    var order_id: String? = null,
    var created_at: String? = null,
    var status_id: Int? = null,
    var complated: Boolean? = null,
    var date_complated: String? = null
)
