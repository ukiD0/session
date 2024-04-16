package com.example.session2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.storage.downloadPublicTo
import io.github.jan.supabase.storage.storage
import java.io.File

class BucketViewModel: ViewModel() {
    private var user: UserInfo? = null
    suspend fun getImage(): ByteArray {
        val backet = DbCon.supabase.storage.from("images")
        val image = backet.downloadPublic("image1.jpg")
        return image
    }


    init {
        user = DbCon.supabase.auth.currentUserOrNull()
    }

    suspend fun uploadImage(byteArray:ByteArray): ByteArray {
        val bucket = DbCon.supabase.storage.from("images")
        bucket.upload("${user?.id}-avatar.png", byteArray, upsert = false)
        return byteArray
    }

}