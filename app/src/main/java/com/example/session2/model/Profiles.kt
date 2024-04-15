package com.example.session2.model

import kotlinx.serialization.Serializable

@Serializable
data class Profiles(
    var id: String? = null,
    var id_user: String? = null,
    var fullname: String? = null,
    var avatar: String? = null,
    var phone: String? = null,
    var balance:Float? = null,
    var rider: Boolean? = null,
    var created_at: String? = null,
    )
