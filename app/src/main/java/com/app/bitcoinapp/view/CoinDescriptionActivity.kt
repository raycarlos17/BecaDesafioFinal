package com.app.bitcoinapp.view

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.helper.SharedPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_description.*

class CoinDescriptionActivity : AppCompatActivity(), View.OnClickListener {

    private var coin: Coin? = null
    private lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_description)

        sharedPreferences = SharedPreferences(this)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        btn_add_favorite.setOnClickListener(this)

        getExtras()
        setupCoin()
        verifyFavorite()
    }

    private fun verifyFavorite() {
        val statusFavorite = sharedPreferences.getBoolean(coin?.assetId.toString())
        if(statusFavorite){
            btn_add_favorite.text = getString(R.string.btn_add_favorite_delete)
            coin?.favorite = false
        }else if(!statusFavorite){
            btn_add_favorite.text = getString(R.string.btn_adicionar)
            coin?.favorite = true
        }
    }


    private fun getExtras(){
        coin = intent.getParcelableExtra("EXTRA_COIN")
    }

    private fun setupCoin(){
        tv_coin_value.text = coin?.priceUsd
        tv_coin_value_ultima_hora.text = coin?.volumeHourUsd
        tv_coin_value_ultimo_dia.text = coin?.volumeDayUsd
        tv_coin_value_ultimo_mes.text = coin?.volumeMthUsd
        Picasso.get().load("${coin?.iconUrl}.png").placeholder(R.drawable.ic_image).into(iv_coin)
        if (sharedPreferences.getBoolean(coin?.assetId.toString())){
            iv_favorite.visibility = View.VISIBLE
        }else if (!sharedPreferences.getBoolean(coin?.assetId.toString())){
            iv_favorite.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_add_favorite){
            val statusBtn = btn_add_favorite.text.toString()
            if(statusBtn == "ADICIONAR"){
                sharedPreferences.storeBoolean(coin?.assetId.toString(),true)
                verifyFavorite()
                setupCoin()
            }else if(statusBtn == "DELETE" ){
                sharedPreferences.storeBoolean(coin?.assetId.toString(),false)
                verifyFavorite()
                setupCoin()
            }

        }
    }
}