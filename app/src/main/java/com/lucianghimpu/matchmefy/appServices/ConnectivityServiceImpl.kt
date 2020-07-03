package com.lucianghimpu.matchmefy.appServices

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityServiceImpl(private val context: Context) :
    ConnectivityService {
    override fun hasConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}