package com.app.bitcoinapp.repository


import com.app.bitcoinapp.model.api.CoinRestApiTask_old
import com.example.commons.model.Coin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinsRepository_old(private val coinRestApiTask: CoinRestApiTask_old) {

    companion object{
        private const val REQUEST_SUCCESS = 200
    }


    fun getAllCoins(onSuccess: (List<Coin>) -> Unit, onError: (Int?, String?) -> Unit){
        val request = coinRestApiTask.retrofitApi().getAllAssets()

        request.enqueue(object: Callback<List<Coin>>{

            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                val responseCode = response.code()


                if(responseCode == REQUEST_SUCCESS){
                    val coinsList = response.body()!!
                    onSuccess.invoke(coinsList)
                }else{
                    onError.invoke(responseCode, null)
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {

                onError.invoke(null, t.toString())
            }
        })
    }
}