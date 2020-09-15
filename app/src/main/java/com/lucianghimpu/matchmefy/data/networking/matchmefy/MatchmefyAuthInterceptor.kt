package com.lucianghimpu.matchmefy.data.networking.matchmefy

import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import okhttp3.Interceptor
import okhttp3.Response

class MatchmefyAuthInterceptor(
    private val sharedPreferencesService: PreferencesService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val requestUrl = chain.request().url.toString()
        val method = chain.request().method

        if (requestUrl.contains("users") && method == "POST") {
            return chain.proceed(chain.request())
        }

        val token = sharedPreferencesService.getString(PreferencesConstants.MATCHMEFY_ACCESS_TOKEN_KEY)

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}