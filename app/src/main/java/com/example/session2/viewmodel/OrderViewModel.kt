package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.Orders
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class OrderViewModel:ViewModel() {
    private var _order: MutableLiveData<Orders> = MutableLiveData()
    private var _user:MutableLiveData<UserInfo> = MutableLiveData()


    val order: LiveData<Orders> = _order

    init {
        _user.value =DbCon.supabase.auth.currentUserOrNull()
    }
    suspend fun getOrder():Orders? {
         return DbCon.supabase.from("orders").select {
            filter {
                Orders::id_user eq _user.value?.id
            }
        }.decodeSingleOrNull<Orders>()
    }

    suspend fun feedback(out_feedback:String): Orders? {
        return DbCon.supabase.from("orders").update({
            Orders::feedback setTo out_feedback
        }
            ){
            select()
            filter {
                Orders::id_user eq _user.value?.id
            }
        }.decodeSingleOrNull<Orders>()
    }

}