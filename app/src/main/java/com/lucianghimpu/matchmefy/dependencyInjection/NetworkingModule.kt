package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.data.networking.MatchmefyRetrofitServiceFactory
import com.lucianghimpu.matchmefy.data.networking.SpotifyRetrofitServiceFactory
import com.lucianghimpu.matchmefy.data.networking.authorizationInterceptor.AuthorizationInterceptor
import com.lucianghimpu.matchmefy.data.networking.authorizationInterceptor.AuthorizationInterceptorImpl
import com.lucianghimpu.matchmefy.data.networking.connectivityInterceptor.ConnectivityInterceptor
import com.lucianghimpu.matchmefy.data.networking.connectivityInterceptor.ConnectivityInterceptorImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.sql.Date
import java.util.*

val networkingModule = module {
    single { ConnectivityInterceptorImpl(get()) as ConnectivityInterceptor }
    single { AuthorizationInterceptorImpl(get()) as AuthorizationInterceptor }
    single { okHttpClient(get(), get()) }
    single { SpotifyRetrofitServiceFactory(get()) }
    single { MatchmefyRetrofitServiceFactory(get()) }
}

private fun okHttpClient (
    connectivityInterceptor: ConnectivityInterceptor,
    authorizationInterceptor: AuthorizationInterceptor) : OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    return OkHttpClient.Builder()
        .addInterceptor(authorizationInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(connectivityInterceptor)
        .build()
}
