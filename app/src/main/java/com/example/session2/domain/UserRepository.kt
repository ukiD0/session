package com.example.session2.domain

import io.github.jan.supabase.gotrue.user.UserInfo

class UserRepository( private val model: UserInterface) {

    fun auth(out_email:String,out_pass:String): UserInfo?{
        return model.auth(out_email,out_pass)
    }
    fun registration(out_email:String,out_pass:String,fullname:String,out_number:String): UserInfo?{
        return model.registration(out_email,out_pass,fullname,out_number)
    }

}