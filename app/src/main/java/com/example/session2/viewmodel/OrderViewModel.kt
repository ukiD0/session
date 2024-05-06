package com.example.session2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.OrderStatus
import com.example.session2.model.Orders
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class OrderViewModel:ViewModel() {
    private var _order: MutableLiveData<Orders> = MutableLiveData()
    private var _user:MutableLiveData<UserInfo> = MutableLiveData()
    private var _orderStatus = MutableLiveData<List<OrderStatus>>()
    val orderStatus:LiveData<List<OrderStatus>> = _orderStatus


    val order: LiveData<Orders> = _order

    init {
        _user.value =DbCon.supabase.auth.currentUserOrNull()
    }
    suspend fun getOrder(): Orders? {
        return DbCon.supabase.from("orders").select {
            filter {
                Orders::id_user eq _user.value?.id
            }
        }.decodeSingleOrNull<Orders>()
    }
    suspend fun getOrderStatus(id: String):Boolean{
        return try {
            _orderStatus.value = DbCon.supabase.from("order_status").select {
                filter {
                    OrderStatus::order_id eq id
                }
            }.decodeList<OrderStatus>()
            Log.e("test", true.toString())
            true
        }catch (e:Exception){
            Log.e("test", e.message.toString())
            false
        }
    }

    suspend fun feedback(out_feedback:String = "null", out_rate: Int = 0): Orders? {
        return DbCon.supabase.from("orders").update({
            Orders::feedback setTo out_feedback
            Orders::rate setTo  out_rate
        }
            ){
            select()
            filter {
                Orders::id_user eq _user.value?.id
            }
        }.decodeSingleOrNull<Orders>()
    }

}