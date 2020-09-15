package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyAuthInterceptor
import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyAuthenticator
import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyRefreshTokenServiceFactory
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

    single { MatchmefyAuthInterceptor(get()) }
    single { MatchmefyRefreshTokenServiceFactory(get()) }
    single { MatchmefyAuthenticator(get(), get()) }
    single { MatchmefyRetrofitServiceFactory(get(), get(), get()) }
}