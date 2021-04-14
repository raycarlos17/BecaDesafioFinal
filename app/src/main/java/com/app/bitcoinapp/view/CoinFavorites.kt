package com.app.bitcoinapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.bitcoinapp.R
import kotlinx.android.synthetic.main.activity_coin_description.*
import kotlinx.android.synthetic.main.coin_favorites_recyclerview.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CoinFavorites : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_favorites_item)

        tv_date_favorites.text = getLocalDate()

    }

    private fun getLocalDate(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return now.format(formatter).toString()
    }
}