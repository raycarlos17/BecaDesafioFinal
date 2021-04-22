package com.example.commons.model.helper

import android.content.Context
import android.net.ConnectivityManager

class ConnectionState {

    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val network = connectivityManager.activeNetwork

        return network != null
    }
}