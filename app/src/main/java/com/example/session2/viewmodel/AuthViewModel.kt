/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.common.DbCon
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import io.github.jan.supabase.gotrue.user.UserInfo

class AuthViewModel: ViewModel() {

    private var _user: MutableLiveData<UserInfo> = MutableLiveData()
    val user: LiveData<UserInfo> =_user

    suspend fun auth(out_email:String,out_pass:String){
        DbCon.supabase.auth.signUpWith(Email){
            email = out_email
            password = out_pass
        }
    }

    suspend fun registration(out_email: String,out_pass: String){
        DbCon.supabase.auth.signInWith(Email){
            email = out_email
            password = out_pass
        }
    }
    fun getUser(){
        _user.value = DbCon.supabase.auth.currentUserOrNull()
    }
    suspend fun google(){
        DbCon.supabase.auth.signInWith(IDToken) {
            idToken = "token"
            provider = Google
            //optional:
//            nonce = "nonce"
//            data = buildJsonObject {
//                //...
//            }
        }
    }
    suspend fun resetPass(out_email: String){
         DbCon.supabase.auth.resetPasswordForEmail(out_email)
    }
    fun modif(out_pass: String){
        out_pass
    }
    suspend fun verifOTP(out_email: String){
        DbCon.supabase.auth.signInWith(OTP){
            email = out_email
        }
    }

}