package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.appServices.Auth.AppAuthService
import com.lucianghimpu.matchmefy.appServices.Connectivity.ConnectivityService
import com.lucianghimpu.matchmefy.appServices.Connectivity.MerlinConnectivityService
import com.lucianghimpu.matchmefy.appServices.EncryptedPreferencesService
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import org.koin.dsl.module

val servicesModule = module {

    single { MerlinConnectivityService(
        get()
    ) as ConnectivityService
    }
    single { EncryptedPreferencesService(get()) as PreferencesService }
    single {
        AppAuthService(
            get(),
            get()
        )
    }
}