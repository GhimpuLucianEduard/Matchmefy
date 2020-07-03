package com.lucianghimpu.matchmefy.data.networking.connectivityInterceptor

import com.lucianghimpu.matchmefy.appServices.ConnectivityService
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    private val connectivityService: ConnectivityService
) : ConnectivityInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityService.hasConnection()) {
            throw NoConnectivityException()
        }

        return chain.proceed(chain.request())
    }
}

