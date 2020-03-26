package com.lucianghimpu.matchmefy.data.networking.authorizationInterceptor

import com.lucianghimpu.matchmefy.services.EncryptedSharedPreferencesServiceImpl
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import com.lucianghimpu.matchmefy.utilities.Preferences.SPOTIFY_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptorImpl(
    private val preferencesServiceImpl: EncryptedSharedPreferencesServiceImpl
) : AuthorizationInterceptor  {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = preferencesServiceImpl.getPreference(SPOTIFY_TOKEN, String.empty)

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}