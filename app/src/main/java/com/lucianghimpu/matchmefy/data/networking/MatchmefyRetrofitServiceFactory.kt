package com.lucianghimpu.matchmefy.data.networking

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lucianghimpu.matchmefy.utilities.ApiConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchmefyRetrofitServiceFactory(
    private val okHttpClient: OkHttpClient
) {
    fun <T> create(service: Class<T>): T {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiConstants.MATCHMEFY_API_BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }
}
