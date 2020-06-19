package com.lucianghimpu.matchmefy.data.networking.authorizationInterceptor

import com.lucianghimpu.matchmefy.services.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import com.lucianghimpu.matchmefy.utilities.Preferences.SPOTIFY_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptorImpl(
    private val preferencesService: EncryptedSharedPreferencesService
) : AuthorizationInterceptor  {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = preferencesService.getPreference(SPOTIFY_TOKEN, String.empty)

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}