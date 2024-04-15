package com.example.session2.model

import kotlinx.serialization.Serializable

@Serializable
data class Profiles(
    var id: String,
    var id_user: String,
    var fullname: String,
    var phone: String,
    var balance:String,
    var rider: String,
    var created_at: String,
    )
