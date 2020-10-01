package com.lucianghimpu.matchmefy.appServices.Connectivity

import android.content.Context
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.novoda.merlin.Merlin
import com.novoda.merlin.MerlinsBeard
import timber.log.Timber


class MerlinConnectivityService(
    context: Context
) : ConnectivityService {

    private val merlin = Merlin
        .Builder()
        .withConnectableCallbacks()
        .withDisconnectableCallbacks()
        .build(context)

    private val merlinBeard = MerlinsBeard
        .Builder()
        .build(context)

    private var hasConnection: Boolean = false

    private var connectivityServiceListener: ConnectivityServiceListener? = null

    override fun start() {
        Timber.d("Starting Merlin Connectivity Service")
        merlin.registerConnectable(this::onConnected)
        merlin.registerDisconnectable(this::onDisconnected)
        merlin.bind()
    }

    override fun stop() {
        Timber.d("Stopping Merlin Connectivity Service")
        merlin.unbind()
    }

    override fun hasConnection() = hasConnection

    override fun setConnectivityListener(connectivityServiceListener: ConnectivityServiceListener) {
        this.connectivityServiceListener = connectivityServiceListener
    }

    private fun checkInternetConnection() {
        merlinBeard.hasInternetAccess { hasAccess ->
            AppAnalytics.trackLog("Connectivity changed: $hasAccess")
            hasConnection = hasAccess
            connectivityServiceListener?.onConnectivityChanged(hasConnection)
        }
    }

    private fun onConnected() {
        AppAnalytics.trackLog("Connected to a network")
        checkInternetConnection()
    }

    private fun onDisconnected() {
        AppAnalytics.trackLog("Disconnected from a network")
        checkInternetConnection()
    }
}