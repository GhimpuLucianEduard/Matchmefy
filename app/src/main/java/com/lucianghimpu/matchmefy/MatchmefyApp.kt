package com.lucianghimpu.matchmefy

import android.app.Application
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.dependencyInjection.appModule
import com.lucianghimpu.matchmefy.dependencyInjection.dataServicesModule
import com.lucianghimpu.matchmefy.dependencyInjection.networkingModule
import com.lucianghimpu.matchmefy.dependencyInjection.servicesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MatchmefyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@MatchmefyApp)
            modules(
                listOf(
                    servicesModule,
                    networkingModule,
                    dataServicesModule,
                    appModule
                )
            )
        }
        AppAnalytics.initAppAnalytics(this)
    }
}