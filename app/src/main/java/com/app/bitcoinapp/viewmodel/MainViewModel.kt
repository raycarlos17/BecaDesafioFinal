package com.app.bitcoinapp.viewmodel

import android.graphics.Movie
import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val listA = arrayListOf<String>("teste1", "teeste2", "teste3", "teste4", "teste5", "teste6"
    ,"teste6","teste6","teste6","teste6","teste6","teste6","teste6","teste6")

    private var list = MutableLiveData<List<String>>()
    val bitCointList: LiveData<List<String>>
        get() = list

    fun init() {
        setList()
    }

    private fun setList() {
        list.postValue(listA)
    }
}