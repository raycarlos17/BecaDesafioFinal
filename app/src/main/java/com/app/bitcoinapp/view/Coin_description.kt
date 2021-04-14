package com.app.bitcoinapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_description.*

class Coin_description : AppCompatActivity() {

    private var coin: Coin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_description)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        getExtras()
        setupCoin()
    }

    private fun getExtras(){
        coin = intent.getParcelableExtra("EXTRA_COIN")
    }

    private fun setupCoin(){
        tv_coin_value.text = coin?.priceUsd
        tv_coin_value_ultima_hora.text = coin?.volumeHourUsd
        tv_coin_value_ultimo_mes.text = coin?.volumeDayUsd
        tv_coin_value_ultimo_ano.text = coin?.volumeMthUsd
        Picasso.get().load("${coin?.iconUrl}.png").placeholder(R.drawable.ic_image).into(iv_coin)
    }
}