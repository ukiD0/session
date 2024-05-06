package com.example.session2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import com.example.session2.model.Profiles
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class ProfileViewModel:ViewModel() {

    private var _user: MutableLiveData<UserInfo> = MutableLiveData()

    init {
        _user.value = DbCon.supabase.auth.currentUserOrNull()
    }

    suspend fun setProfileData(profileData: Profiles): Profiles?{
        profileData.id_user = _user.value?.id
        val check = DbCon.supabase.from("profiles").select{
            filter {
                Profiles::id_user eq _user.value?.id
            }
        }.decodeSingleOrNull<Profiles>()
        if (check != null){
            return DbCon.supabase.from("profiles").update(
                {
                    Profiles::phone setTo profileData.phone
                    Profiles::fullname setTo  profileData.fullname
                }
            ){
                select()
                filter {
                    Profiles::id_user eq profileData.id_user
                }
            }.decodeSingleOrNull<Profiles>()
        }else{
            return DbCon.supabase.from("profiles").insert(profileData){
                select()
            }.decodeSingleOrNull<Profiles>()
        }
    }
    suspend fun getProfileData(): Profiles?{
        return DbCon.supabase.from("profiles").select{
            filter {
                Profiles::id_user eq _user.value?.id
            }
        }.decodeSingleOrNull<Profiles>()
    }


}