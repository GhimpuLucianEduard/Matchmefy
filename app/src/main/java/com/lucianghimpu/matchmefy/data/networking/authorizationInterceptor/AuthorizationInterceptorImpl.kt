package com.lucianghimpu.matchmefy.data.networking.authorizationInterceptor

import com.lucianghimpu.matchmefy.appServices.AuthService
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptorImpl(
    private val authService: AuthService
) : AuthorizationInterceptor  {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = authService.getToken()

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}