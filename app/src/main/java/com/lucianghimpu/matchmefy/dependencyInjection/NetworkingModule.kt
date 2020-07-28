package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyRetrofitServiceFactory
import com.lucianghimpu.matchmefy.data.networking.shared.connectivityInterceptor.ConnectivityInterceptor
import com.lucianghimpu.matchmefy.data.networking.shared.connectivityInterceptor.ConnectivityInterceptorImpl
import com.lucianghimpu.matchmefy.data.networking.spotify.SpotifyAuthInterceptor
import com.lucianghimpu.matchmefy.data.networking.spotify.SpotifyAuthRetrofitServiceFactory
import com.lucianghimpu.matchmefy.data.networking.spotify.SpotifyAuthenticator
import com.lucianghimpu.matchmefy.data.networking.spotify.SpotifyRetrofitServiceFactory
import org.koin.dsl.module

val networkingModule = module {
    single { ConnectivityInterceptorImpl(get()) as ConnectivityInterceptor }
    single { SpotifyAuthInterceptor(get()) }
    single { SpotifyAuthRetrofitServiceFactory(get()) }
    single { SpotifyAuthenticator(get(), get()) }
    single { SpotifyRetrofitServiceFactory(get(), get(), get()) }
    single { MatchmefyRetrofitServiceFactory(get()) }
}