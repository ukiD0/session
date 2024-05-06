package com.example.session2.model

import kotlinx.serialization.Serializable

@Serializable
data class Orders(
    var id: String? = null,
    var id_user: String? = null,
    var address: String? = null,
    var country: String? = null,
    var phone: String? = null,
    var others: String? = null,
    var package_items: String? = null,
    var weight_items: Int? = null,
    var worth_items: Int? = null,
    var created_at: String? = null,
    var payed: Boolean? = null,
    var status: Int? = null,
    var succesed: Boolean? = null,
    var rate: Int? = null,
    var feedback: String? = null,
    var delivery_charges: String? = null,
    var instant_delivery: String? = null,
    var tax_and_service_charges: String? = null,
    var sum_price: String? = null,
    var lat: String? = null,
    var long: String? = null

)