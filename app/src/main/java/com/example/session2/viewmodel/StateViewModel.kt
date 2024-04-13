package com.example.session2.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateViewModel : ViewModel() {
    private var _nameCards = MutableLiveData<String>()
    private var _cardVisible = MutableLiveData<Boolean>()
    private var _bottomMentuVisible = MutableLiveData<Boolean>()

    val nameCards:LiveData<String> = _nameCards
    val cardVisible:LiveData<Boolean> = _cardVisible
    val bottomMenuVisible:LiveData<Boolean> = _bottomMentuVisible

    init {
        _nameCards.value = "Main"
        _cardVisible.value = false
        _bottomMentuVisible.value = false

    }

    fun setVisible(yesno:Boolean){
        _cardVisible.value = yesno
    }
    fun setTitle(title: String){
        _nameCards.value = title
    }
    fun setBottomVisible(yesno: Boolean){
        _bottomMentuVisible.value = yesno
    }
}