package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.Points
import io.github.jan.supabase.postgrest.from

class PointsViewModel: ViewModel() {
    private var _points:MutableLiveData<List<Points>> = MutableLiveData()
    val points:LiveData<List<Points>> = _points
    suspend fun getPoint():Boolean{
        return try {
            val points = DbCon.supabase.from("points").select().decodeList<Points>()
            _points.value = points
            true
        }catch (e:Exception){
            false
        }

    }

}