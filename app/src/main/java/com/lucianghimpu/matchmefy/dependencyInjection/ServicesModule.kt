package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.services.ConnectivityService
import com.lucianghimpu.matchmefy.services.ConnectivityServiceImpl
import com.lucianghimpu.matchmefy.services.EncryptedSharedPreferencesServiceImpl
import com.lucianghimpu.matchmefy.services.SpotifyAuthService
import org.koin.dsl.module

val servicesModule = module {

    single { ConnectivityServiceImpl(get()) as ConnectivityService }

    single {
        EncryptedSharedPreferencesServiceImpl(
            get()
        )
    }

    single { SpotifyAuthService() }
}
