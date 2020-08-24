package com.lucianghimpu.matchmefy.appServices.Connectivity

interface ConnectivityService {
    fun start()
    fun stop()
    fun hasConnection(): Boolean
    fun setConnectivityListener(connectivityServiceListener: ConnectivityServiceListener)
}