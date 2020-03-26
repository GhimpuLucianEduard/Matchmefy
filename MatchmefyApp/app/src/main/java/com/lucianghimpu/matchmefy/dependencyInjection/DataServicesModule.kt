package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.data.services.SpotifyService
import com.lucianghimpu.matchmefy.data.services.SpotifyServiceImpl
import org.koin.dsl.module

val dataServicesModule = module {
    single { SpotifyServiceImpl(get()) as SpotifyService }
}