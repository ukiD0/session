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
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


class AuthViewModel() : ViewModel()  {

    private var _user: MutableLiveData<UserInfo> = MutableLiveData()
    private var _currentEmail: MutableLiveData<String> = MutableLiveData()
    private var _currentPhone: MutableLiveData<String> = MutableLiveData()

    val user: LiveData<UserInfo> =_user
    val currentEmail: LiveData<String> = _currentEmail
    var currentPhone: LiveData<String> = _currentPhone


    suspend fun auth(out_email:String,out_pass:String): User,Info? {
        DbCon.supabase.auth.signUpWith(Email){
            email = out_email
            password = out_pass
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
    suspend fun modifUser(out_pass: String): UserInfo? {
         DbCon.supabase.auth.modifyUser{
             password = out_pass
         }
        return DbCon.supabase.auth.currentUserOrNull()
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
    suspend fun resendOTP(out_email:String): UserInfo? {
        DbCon.supabase.auth.resendEmail(OtpType.Email.SIGNUP,out_email)
        return DbCon.supabase.auth.currentUserOrNull()
    }
     suspend fun editProfile(out_phone: String): UserInfo? {
         _currentPhone.value = out_phone
         DbCon.supabase.auth.modifyUser {
            phone = out_phone
         }
         return DbCon.supabase.auth.currentUserOrNull()
     }

    suspend fun updateData(out_phone: String,profiles: Profiles){
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