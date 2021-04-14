package com.app.bitcoinapp.model.helper

import com.app.bitcoinapp.model.Coin

interface ClickItemListener {
    fun ClickItemList(coin: Coin)
}