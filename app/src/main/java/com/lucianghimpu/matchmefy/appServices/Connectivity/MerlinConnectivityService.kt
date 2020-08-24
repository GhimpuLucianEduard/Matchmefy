package com.lucianghimpu.matchmefy.appServices.Connectivity

import android.content.Context
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.novoda.merlin.Merlin
import timber.log.Timber


class MerlinConnectivityService(
    context: Context
) : ConnectivityService {

    private val merlin = Merlin
        .Builder()
        .withConnectableCallbacks()
        .withDisconnectableCallbacks()
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

    override fun hasConnection(): Boolean = hasConnection

    override fun setConnectivityListener(connectivityServiceListener: ConnectivityServiceListener) {
        this.connectivityServiceListener = connectivityServiceListener
    }

    private fun onConnected() {
        hasConnection = true
        AppAnalytics.trackLog("Connected to Internet")
        connectivityServiceListener?.onConnected()
    }

    private fun onDisconnected() {
        hasConnection = false
        AppAnalytics.trackLog("Disconnected from Internet")
        connectivityServiceListener?.onDisconnected()
    }
}