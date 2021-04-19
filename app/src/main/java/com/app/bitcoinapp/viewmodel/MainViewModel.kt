package com.app.bitcoinapp.viewmodel

import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.api.CoinRestApiTask
import com.app.bitcoinapp.repository.CoinsRepository
import com.app.bitcoinapp.model.helper.AlertDialog
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
        const val UNAUTHORIZED_CODE = 401
        const val FORBIDDEN_CODE = 403
        const val MAX_REQUESTS_CODE = 429
        const val NO_DATA_CODE = 550
    }

    private var _bitCoinsList = MutableLiveData<List<Coin>>()
    val bitCoinsList: LiveData<List<Coin>>
        get() = _bitCoinsList

    fun init(fragmentManager: FragmentManager, isConnected: Boolean) {
        supportFragmentManager = fragmentManager

        if(isConnected){
            getAllCoins()
        }else{
            AlertDialog("Sem conexão \n\nPor favor, verifique sua rede e tente novamente").show(supportFragmentManager, "No_Connection")
        }

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
            (code == BAD_REQUEST_CODE) -> {alert = AlertDialog("Erro $code: ${R.string.bad_request}")
            }
            (code == UNAUTHORIZED_CODE) -> {alert = AlertDialog("Erro $code: ${R.string.unauthorized}")
            }
            (code == FORBIDDEN_CODE) -> {alert = AlertDialog("Erro $code: ${R.string.forbiden}")
            }
            (code == MAX_REQUESTS_CODE) -> {alert = AlertDialog("Erro $code: ${R.string.max_request}")
            }
            (code == NO_DATA_CODE) -> {alert = AlertDialog("Erro $code: ${R.string.no_data}")
            }
            (code == null && message != null) -> {alert = AlertDialog(message)
            }
            else -> {alert = AlertDialog("Ops tivemos um problema, tente novamente mais tarde!")
            }
        }

        alert.show(supportFragmentManager, "Error")
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