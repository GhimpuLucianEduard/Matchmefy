package com.lucianghimpu.matchmefy.data.networking.spotify

import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.SPOTIFY_ACCESS_TOKEN_KEY
import okhttp3.Interceptor
import okhttp3.Response

class SpotifyAuthInterceptor(
    private val sharedPreferencesService: PreferencesService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = sharedPreferencesService.getString(SPOTIFY_ACCESS_TOKEN_KEY)

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}