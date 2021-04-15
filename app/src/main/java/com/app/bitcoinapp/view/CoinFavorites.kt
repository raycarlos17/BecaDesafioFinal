package com.app.bitcoinapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.helper.SetDate
import kotlinx.android.synthetic.main.coin_favorites_recyclerview.*

class CoinFavorites : AppCompatActivity() {

    private val setDate = SetDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_favorites_recyclerview)

        tv_date_favorites.text = setDate.getLocalDate()
        tv_date_favorites.contentDescription = setDate.getLocalDate()

    }
}