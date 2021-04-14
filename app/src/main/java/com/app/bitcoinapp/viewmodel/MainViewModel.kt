package com.app.bitcoinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.api.CoinRestApiTask
import com.app.bitcoinapp.repository.CoinsRepository
import java.text.NumberFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val coinRestApiTask = CoinRestApiTask()
    private val coinsRepository = CoinsRepository(coinRestApiTask)
    private val usd = Currency.getInstance("USD")
    private var formatUSDCurrency = NumberFormat.getCurrencyInstance(Locale.US)

    companion object{
        const val IMG_URL = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/"
    }

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
        list.forEach{
            it.priceUsd = formatCurrency(it.priceUsd)
            it.volumeDayUsd = formatCurrency(it.volumeDayUsd)
            it.volumeHourUsd = formatCurrency(it.volumeHourUsd)
            it.volumeMthUsd = formatCurrency(it.volumeMthUsd)
            it.iconUrl = formatIconUrl(it.iconId)
        }
        _bitCoinsList.postValue(cryptoCurrencyList)
    }

    private fun onRequestError(code: Int?){
        //Ainda não implementado
        Log.d("MainViewModel: ", "Error code $code")
    }

    private fun filterCryptoCurrency(list: List<Coin>): List<Coin> {
        return list.filter {element -> element.isCrypto == 1}
    }

    private fun formatCurrency(value: String?): String{
        formatUSDCurrency.currency = usd

        return if(value != null){
            formatUSDCurrency.format(value.toDouble())
        }else{
            "$0"
        }
    }

    private fun formatIconUrl(iconId: String?): String?{
        return if(iconId != null){
            "$IMG_URL${iconId.replace("-", "")}"
        }else{
            iconId
        }
    }
}