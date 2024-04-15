package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel:ViewModel() {
    private  var _fullname: MutableLiveData<String> = MutableLiveData()
    private var _phone: MutableLiveData<String> = MutableLiveData()
    private var _avatar:MutableLiveData<String> = MutableLiveData()
    private var _balance: MutableLiveData<String> = MutableLiveData()
    private var _rider: MutableLiveData<String> = MutableLiveData()

    val fullname:LiveData<String> = _fullname
    val phone:LiveData<String> = _phone
    val avatar:LiveData<String> = _avatar
    val balance: LiveData<String> = _balance
    val rider: LiveData<String> = _rider


}