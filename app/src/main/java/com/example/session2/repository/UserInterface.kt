package com.example.session2.repository

import io.github.jan.supabase.gotrue.user.UserInfo

interface UserInterface {

    fun auth(out_email:String,out_pass:String): UserInfo?
    fun registration(out_email:String,out_pass:String,fullname:String,out_number:String): UserInfo?



}