package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.Transactions
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class TransactionViewModel:ViewModel() {
    private var _user:MutableLiveData<UserInfo> = MutableLiveData()
    private var _trans: MutableLiveData<List<Transactions>> = MutableLiveData()

    val trans: LiveData<List<Transactions>> = _trans

    init {
        _user.value = DbCon.supabase.auth.currentUserOrNull()
    }

    suspend fun getTransaction(){
        _trans.value = DbCon.supabase.from("transactions").select{
            filter {
                Transactions::id_user eq _user.value?.id
            }
        }.decodeList()
    }
}