package com.example.commons.model.helper

import android.content.Context
import com.example.commons.model.Coin
import com.google.gson.Gson

class SharedPreferences(context: Context) {

    private val mSharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE)

    fun storeCoinAsJson(coin: Coin?){
        val jsonCoin = Gson().toJson(coin)
        mSharedPreferences.edit().putString(coin?.assetId, jsonCoin).apply()
    }

    fun getCoinFromJson(key: String): Coin? {
        val coinJson = mSharedPreferences.getString(key, null)
        val coinObject: Coin?
        if (coinJson != null){
            coinObject = Gson().fromJson(coinJson, Coin::class.java)
        }else{
            coinObject = null
        }

        return coinObject
    }

    fun getAllFavoriteCoins(): MutableList<Coin> {
        val coinsList: MutableList<Coin> = mutableListOf()
        val preferences = mSharedPreferences.all.values
        preferences.forEach {
            if(it is String){
                val coin: Coin = Gson().fromJson(it.toString(), Coin::class.java)
                coinsList.add(coin)
            }
        }
        return coinsList
    }

    fun cleanSharedPreferences(){
        val list = mSharedPreferences.all.keys

        list.forEach {
            removeKey(it)
        }
    }

    fun storeBoolean(key: String, Value: Boolean){
        mSharedPreferences.edit().putBoolean(key,Value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false) ?: false
    }

    fun removeKey(key: String){
        mSharedPreferences.edit().remove(key).apply()
    }

    fun containsKey(key: String): Boolean {
        return mSharedPreferences.contains(key)
    }
}