package com.lucianghimpu.matchmefy.data.networking.authorizationInterceptor

import android.util.Log
import com.lucianghimpu.matchmefy.appServices.AuthService
import com.lucianghimpu.matchmefy.appServices.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import net.openid.appauth.AuthState
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptorImpl(
    private val authService: AuthService
) : AuthorizationInterceptor  {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = authService.getToken()
        Log.i(LOG_TAG, "Token is null: ${token.isNullOrEmpty()}")

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}