package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyServiceImpl
import com.lucianghimpu.matchmefy.data.dataServices.SpotifyService
import com.lucianghimpu.matchmefy.data.dataServices.SpotifyServiceImpl
import org.koin.dsl.module

val dataServicesModule = module {
    single { SpotifyServiceImpl(get()) as SpotifyService }
    single { MatchmefyServiceImpl(get()) as MatchmefyService}
}