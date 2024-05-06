package com.example.session2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import io.github.jan.supabase.postgrest.from

class DestinationViewModel:ViewModel() {

    private var _destinationDet: MutableLiveData<destinations_details> = MutableLiveData()

    suspend fun getDestinDetail(id: String?): destinations_details?{
        return DbCon.supabase.from("destinations_details").select {
            filter {
                destinations_details:: id_order eq id
            }
        }.decodeSingleOrNull<destinations_details>()
    }

}