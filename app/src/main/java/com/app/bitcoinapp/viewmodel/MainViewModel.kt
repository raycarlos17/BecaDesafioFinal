package com.app.bitcoinapp.viewmodel

import android.app.Application
import android.app.Dialog
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.api.CoinRestApiTask
import com.app.bitcoinapp.repository.CoinsRepository
import com.app.bitcoinapp.view.AlertDialog
import java.lang.StringBuilder
import java.text.NumberFormat
import java.util.*

class MainViewModel : ViewModel() {

    private lateinit var supportFragmentManager: FragmentManager
    private val coinRestApiTask = CoinRestApiTask()
    private val coinsRepository = CoinsRepository(coinRestApiTask)
    private val usd = Currency.getInstance("USD")
    private var formatUSDCurrency = NumberFormat.getCurrencyInstance(Locale.US)

    companion object{
        const val IMG_URL = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/"
        const val BAD_REQUEST_CODE = 400
        const val BAD_REQUEST_MESSAGE = "Bad Request -- There is something wrong with your request"
        const val UNAUTHORIZED_CODE = 401
        const val UNAUTHORIZED_MESSAGE = "Unauthorized -- Your API key is wrong"
        const val FORBIDDEN_CODE = 403
        const val FORBIDDEN_MESSAGE = "Forbidden -- Your API key doesnt't have enough privileges to access this resource"
        const val MAX_REQUESTS_CODE = 429
        const val MAX_REQUESTS_MESSAGE = "Too many requests -- You have exceeded your API key rate limits"
        const val NO_DATA_CODE = 550
        const val NO_DATA_MESSAGE = "No data -- You requested specific single item that we don't have at this moment."
    }

    private var _bitCoinsList = MutableLiveData<List<Coin>>()
    val bitCoinsList: LiveData<List<Coin>>
        get() = _bitCoinsList

    fun init(fragmentManager: FragmentManager) {
        getAllCoins()
        supportFragmentManager = fragmentManager
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

    private fun onRequestError(code: Int?, message: String?){
        var alert: DialogFragment?
        when {
            (code == BAD_REQUEST_CODE) -> {alert = AlertDialog("Erro $code: $BAD_REQUEST_MESSAGE")}
            (code == UNAUTHORIZED_CODE) -> {alert = AlertDialog("Erro $code: $UNAUTHORIZED_MESSAGE")}
            (code == FORBIDDEN_CODE) -> {alert = AlertDialog("Erro $code: $FORBIDDEN_MESSAGE")}
            (code == MAX_REQUESTS_CODE) -> {alert = AlertDialog("Erro $code: $MAX_REQUESTS_MESSAGE")}
            (code == NO_DATA_CODE) -> {alert = AlertDialog("Erro $code: $NO_DATA_MESSAGE")}
            (code == null && message != null) -> {alert = AlertDialog(message)}
            else -> {alert = AlertDialog("Ops tivemos um problema, tente novamente mais tarde!")}
        }

        alert?.show(supportFragmentManager, "Error")
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