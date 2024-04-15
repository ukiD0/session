package com.example.session2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.session2.model.Profiles

class ProfileViewModel:ViewModel() {
    private var _profileModel:MutableLiveData<Profiles> = MutableLiveData()

    val profileModel:LiveData<Profiles> = _profileModel




}