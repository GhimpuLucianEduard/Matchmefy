package com.lucianghimpu.matchmefy.appServices.Connectivity

interface ConnectivityServiceListener {
    fun onConnectivityChanged(hasConnection: Boolean)
}