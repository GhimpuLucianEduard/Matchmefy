package com.lucianghimpu.matchmefy

import android.app.Application
import com.lucianghimpu.matchmefy.dependencyInjection.appModule
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
            modules(appModule)
        }
    }
}