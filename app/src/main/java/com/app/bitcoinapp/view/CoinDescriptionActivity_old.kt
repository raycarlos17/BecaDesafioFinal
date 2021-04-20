package com.app.bitcoinapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.bitcoinapp.R
import com.example.commons.model.Coin
import com.example.commons.model.helper.SharedPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_navigation_view_component.btn_detail
import kotlinx.android.synthetic.main.bottom_navigation_view_component.btn_main
import kotlinx.android.synthetic.main.activity_coin_description.*

class CoinDescriptionActivity_old : AppCompatActivity(), View.OnClickListener {

    private var coin: Coin? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_description)

        sharedPreferences = SharedPreferences(this)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        btn_add_favorite.setOnClickListener(this)
        btn_detail.setOnClickListener(this)
        btn_main.setOnClickListener(this)
        ll_button_back.setOnClickListener(this)

        getExtras()
        setupCoin()
        verifyFavorite()
    }

    private fun verifyFavorite() {
        val statusFavorite = sharedPreferences.getBoolean(coin?.assetId.toString())
        if (statusFavorite) {
            btn_add_favorite.text = getString(R.string.btn_add_favorite_delete)
            btn_add_favorite.contentDescription = getString(R.string.btn_add_favorite_delete)
            coin?.favorite = false
        } else if (!statusFavorite) {
            btn_add_favorite.text = getString(R.string.btn_adicionar)
            btn_add_favorite.contentDescription = getString(R.string.btn_adicionar)
            coin?.favorite = true
        }
    }


    private fun getExtras() {
        coin = intent.getParcelableExtra("EXTRA_COIN")
    }

    private fun setupCoin() {
        tv_coin_sigla.text = coin?.assetId
        tv_coin_sigla.contentDescription = "Sigla da moeda ${coin?.assetId}"

        tv_coin_value.text = coin?.priceUsd
        tv_coin_value.contentDescription = "Valor da moeda ${coin?.priceUsd}"

        tv_coin_value_ultima_hora.text = coin?.volumeHourUsd
        tv_coin_value_ultima_hora.contentDescription = "Valor da última hora ${coin?.volumeHourUsd}"

        tv_coin_value_ultimo_dia.text = coin?.volumeDayUsd
        tv_coin_value_ultimo_dia.contentDescription = "Valor do último dia ${coin?.volumeDayUsd}"

        tv_coin_value_ultimo_mes.text = coin?.volumeMthUsd
        tv_coin_value_ultimo_mes.contentDescription = "Valor do último mês ${coin?.volumeMthUsd}"

        Picasso.get().load("${coin?.iconUrl}.png").placeholder(R.drawable.ic_image).into(iv_coin)

        if (sharedPreferences.getBoolean(coin?.assetId.toString())) {
            iv_favorite.visibility = View.VISIBLE
        } else if (!sharedPreferences.getBoolean(coin?.assetId.toString())) {
            iv_favorite.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btn_add_favorite -> {
                val statusBtn = btn_add_favorite.text.toString()
                if (statusBtn == "ADICIONAR") {
                    sharedPreferences.storeBoolean(coin?.assetId.toString(), true)
                    verifyFavorite()
                    setupCoin()
                } else if (statusBtn == "REMOVER") {
                    sharedPreferences.storeBoolean(coin?.assetId.toString(), false)
                    verifyFavorite()
                    setupCoin()
                }
            }
            R.id.ll_button_back -> {
                backButton()
            }
            R.id.btn_main -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                backButton()
            }
            R.id.btn_detail -> {
                val intentFavorite = Intent(this, FavoriteActivity_old::class.java)
                startActivity(intentFavorite)
            }
        }
    }

    private fun backButton() {
        finish()
    }
}
