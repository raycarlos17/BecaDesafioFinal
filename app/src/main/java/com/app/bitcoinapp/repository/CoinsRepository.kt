package com.app.bitcoinapp.repository

import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.api.CoinRestApiTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinsRepository(private val coinRestApiTask: CoinRestApiTask) {

    companion object{
        private const val REQUEST_SUCCESS = 200
    }


    fun getAllCoins(onSuccess: (List<Coin>) -> Unit, onError: (Int?) -> Unit){
        val request = coinRestApiTask.retrofitApi().getAllAssets()
        println("getAllCoins")

        request.enqueue(object: Callback<List<Coin>>{

            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                println("onResponse")
                val responseCode = response.code()


                if(responseCode == REQUEST_SUCCESS){
                    val coinsList = response.body()!!
                    onSuccess.invoke(coinsList)
                }else{
                    onError.invoke(responseCode)
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
//                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}