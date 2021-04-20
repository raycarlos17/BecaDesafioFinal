package com.app.bitcoinapp.model.api

import com.example.commons.model.Coin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CoinAPI {

    companion object{
        const val API_KEY = "2D512F6E-2307-4B7C-8A0B-ED6E238D3630"
        const val ALL_ASSETS_PATH = "v1/assets"
    }

    @Headers(
        "Accept: application/json",
        "X-CoinAPI-Key: $API_KEY"
    )


    @GET(ALL_ASSETS_PATH)
    fun getAllAssets(): Call<List<Coin>>


}