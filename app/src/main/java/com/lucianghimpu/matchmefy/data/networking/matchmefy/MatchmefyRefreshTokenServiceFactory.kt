package com.lucianghimpu.matchmefy.data.networking.matchmefy

import com.lucianghimpu.matchmefy.data.networking.shared.connectivityInterceptor.ConnectivityInterceptor
import com.lucianghimpu.matchmefy.utilities.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchmefyRefreshTokenServiceFactory(
    private val connectivityInterceptor: ConnectivityInterceptor
) {
    fun <T> create(service: Class<T>): T {
        return Retrofit.Builder()
            .client(okHttpClient())
            .baseUrl(ApiConstants.MATCHMEFY_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }

    private fun okHttpClient () : OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(connectivityInterceptor)
            .build()
    }
}