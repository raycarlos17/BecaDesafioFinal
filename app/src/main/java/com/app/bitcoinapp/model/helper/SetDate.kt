package com.app.bitcoinapp.model.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SetDate {

    fun getLocalDate(): String{
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return now.format(formatter).toString()
    }
}