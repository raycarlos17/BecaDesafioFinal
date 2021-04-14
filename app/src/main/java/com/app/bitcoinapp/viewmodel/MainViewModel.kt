package com.app.bitcoinapp.viewmodel

import android.content.Context
import android.graphics.Movie
import android.telecom.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.api.CoinRestApiTask
import com.app.bitcoinapp.repository.CoinsRepository

class MainViewModel : ViewModel() {

    private val coinRestApiTask = CoinRestApiTask()
    private val coinsRepository = CoinsRepository(coinRestApiTask)

    private var _bitCoinsList = MutableLiveData<List<Coin>>()
    val bitCoinsList: LiveData<List<Coin>>
        get() = _bitCoinsList

    fun init() {
        getAllCoins()
    }

    private fun getAllCoins(){
        coinsRepository.getAllCoins(::onRequestSuccess, ::onRequestError)
    }

    private fun onRequestSuccess(list: List<Coin>){
        val cryptoCurrencyList = filterCryptoCurrency(list)
        _bitCoinsList.postValue(cryptoCurrencyList)
    }

    private fun onRequestError(code: Int?){
        //Ainda não implementado
        Log.d("MainViewModel: ", "Error code $code")
    }

    private fun filterCryptoCurrency(list: List<Coin>): List<Coin> {
        return list.filter {element -> element.isCrypto == 1}
    }
}