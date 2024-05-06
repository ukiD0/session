package com.example.session2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.Profiles
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage

class BucketViewModel: ViewModel() {
    private var user: UserInfo? = null
    suspend fun getImage(): ByteArray? {
        try {
            val backet = DbCon.supabase.storage.from("images")
            val image = backet.downloadAuthenticated("${user?.id}/image1.jpg")
//            Log.e("image?",backet.downloadAuthenticated("${user?.id}/image1.jpg").decodeToString())
            return image
        }catch (e:Exception){
            return null
        }
    }
    init {
        user = DbCon.supabase.auth.currentUserOrNull()
    }
    suspend fun uploadImage(byteArray:ByteArray): ByteArray {
        val bucket = DbCon.supabase.storage.from("images")
        bucket.upload("${user?.id}/image1.jpg", byteArray, upsert = true)
        val url =  DbCon.supabase.storage.from("images").publicUrl("${user?.id}/image1.jpg")
        Log.e("url",url)
        DbCon.supabase.from("profiles").update({
            Profiles::avatar setTo url
        }
        ){
            select()
            filter {
                Profiles::id_user eq user?.id
            }
        }
        return byteArray
    }

//    suspend fun createURL(){
//        val bucket = DbCon.supabase.storage.from("images")
//        val url = bucket.createSignedUrl("${user?.id}/image1.jpg",)
//
//        }

}