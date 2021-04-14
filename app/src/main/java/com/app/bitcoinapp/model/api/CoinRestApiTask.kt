package com.app.bitcoinapp.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinRestApiTask {

    companion object {
        const val BASE_URL = "https://rest-sandbox.coinapi.io/"
    }

    private fun assetsProvider(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    fun retrofitApi(): CoinAPI = assetsProvider().create(CoinAPI::class.java)
}