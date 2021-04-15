package com.app.bitcoinapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_description.*

class CoinDescriptionActivity : AppCompatActivity() {

    private var coin: Coin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_description)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        getExtras()
        setupCoin()

        ll_button_back.setOnClickListener {
            backButton()
        }
    }


    private fun getExtras(){
        coin = intent.getParcelableExtra("EXTRA_COIN")
    }

    private fun setupCoin(){
        tv_coin_value.text = coin?.priceUsd
        tv_title_btc.text = coin?.assetId
        tv_coin_value_ultima_hora.text = coin?.volumeHourUsd
        tv_coin_value_ultimo_dia.text = coin?.volumeDayUsd
        tv_coin_value_ultimo_mes.text = coin?.volumeMthUsd
        Picasso.get().load("${coin?.iconUrl}.png").placeholder(R.drawable.ic_image).into(iv_coin)
    }

    private fun backButton() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
