package com.example.session2.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.Points
import com.squareup.picasso.Picasso
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.runtime.image.ImageProvider
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.awaitAll

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