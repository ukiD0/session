package com.example.session2.model

import kotlinx.serialization.Serializable

@Serializable
data class Transactions(
    var id: String? = null,
    var id_user: String? = null,
    var sum: String? = null,
    var title: String? = null,
    var created_at: String? = null
)
