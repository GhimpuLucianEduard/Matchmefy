package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.services.*
import org.koin.dsl.module

val servicesModule = module {

    single { ConnectivityServiceImpl(get()) as ConnectivityService }
    single { EncryptedSharedPreferencesServiceImpl(get()) as EncryptedSharedPreferencesService }
    single { ResourceProviderImpl(get()) as ResourceProvider }
    single { SpotifyAuthService() }
}
