package com.lucianghimpu.matchmefy.data.networking.spotify

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lucianghimpu.matchmefy.data.networking.shared.connectivityInterceptor.ConnectivityInterceptor
import com.lucianghimpu.matchmefy.utilities.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class SpotifyRetrofitServiceFactory(
    private val spotifyAuthInterceptor: SpotifyAuthInterceptor,
    private val connectivityInterceptor: ConnectivityInterceptor,
    private val spotifyAuthenticator: SpotifyAuthenticator
) {
    fun <T> create(service: Class<T>): T {
        return Retrofit.Builder()
            .client(okHttpClient())
            .baseUrl(ApiConstants.SPOTIFY_API_BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }

    private fun okHttpClient () : OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(spotifyAuthInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(connectivityInterceptor)
            .authenticator(spotifyAuthenticator)
            .build()
    }
}



