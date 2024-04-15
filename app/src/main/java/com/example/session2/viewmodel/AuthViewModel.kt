/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.session2.common.DbCon
import com.example.session2.model.Profiles
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthViewModel() : ViewModel()  {

    private var _user: MutableLiveData<UserInfo> = MutableLiveData()
    private var _currentEmail: MutableLiveData<String> = MutableLiveData()
    val user: LiveData<UserInfo> =_user
    val currentEmail: LiveData<String> = _currentEmail


    suspend fun auth(out_email:String,out_pass:String): UserInfo? {
        DbCon.supabase.auth.signUpWith(Email){
            email = out_email
            password = out_pass
            data = buildJsonObject {
                put("phone",_user.value?.phone)
            }
        }
        return DbCon.supabase.auth.currentUserOrNull()
    }

    suspend fun registration(out_email: String,out_pass: String): UserInfo? {
        DbCon.supabase.auth.signInWith(Email){
            email = out_email
            password = out_pass
        }
        return DbCon.supabase.auth.currentUserOrNull()
    }
    suspend fun google(): UserInfo? {
        DbCon.supabase.auth.signInWith(IDToken) {
            idToken = "token"
            provider = Google
        }
        return DbCon.supabase.auth.currentUserOrNull()
    }
    suspend fun logotttt(){
        DbCon.supabase.auth.signOut()
    }
    suspend fun modifUser(out_pass: String){
         DbCon.supabase.auth.modifyUser{
             password = out_pass
         }
    }
    suspend fun verifOTP(out_email: String){
        _currentEmail.value = out_email
        DbCon.supabase.auth.signInWith(OTP){
            email = out_email
        }
    }
    suspend fun checkOTP(out_email:String,out_token:String): UserInfo? {
        DbCon.supabase.auth.verifyEmailOtp(OtpType.Email.EMAIL,out_email,out_token)
        return DbCon.supabase.auth.currentUserOrNull()
    }
//    suspend fun sendDatatoProfile(out_id:String, out_phone: String,fullname_out: String) {
//        val data = Profiles(phone = out_phone, fullname = fullname_out,)
//        DbCon.supabase.from("Profiles").insert(data)
//    }
    suspend fun selectData(out_phone: String,profiles: Profiles){
        val response = DbCon.supabase.from("Profiles").update({
            Profiles::phone setTo out_phone
        }
        ){
            filter {
                Profiles::id_user eq profiles.id_user
            }
            select()
        }
    return response.decodeSingle()
    }

}