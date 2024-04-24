package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.Orders
import com.example.session2.model.destinations_details
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class DestinationViewModel:ViewModel() {

    private var _destinationDet: MutableLiveData<destinations_details> = MutableLiveData()
    private var _user: MutableLiveData<UserInfo> = MutableLiveData()
    private var _order: MutableLiveData<Orders> = MutableLiveData()

    val destinationDet: LiveData<destinations_details> = _destinationDet

    suspend fun getDestinDetail(): destinations_details?{
        return DbCon.supabase.from("destinations_details").select {
            filter {
                destinations_details:: id_order eq _order.value?.id
            }
        }.decodeSingleOrNull<destinations_details>()
    }

}