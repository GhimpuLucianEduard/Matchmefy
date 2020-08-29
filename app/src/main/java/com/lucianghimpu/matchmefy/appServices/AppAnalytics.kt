package com.lucianghimpu.matchmefy.appServices

import android.app.Application
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.lucianghimpu.matchmefy.BuildConfig
import timber.log.Timber

class AppAnalytics {
    companion object {

        private lateinit var firebaseAnalytics: FirebaseAnalytics
        private lateinit var firebaseCrashlytics: FirebaseCrashlytics

        fun initAppAnalytics(application: Application) {

            firebaseAnalytics = FirebaseAnalytics.getInstance(application)
            firebaseCrashlytics = FirebaseCrashlytics.getInstance()

            if (BuildConfig.DEBUG) {
                FirebaseCrashlytics.getInstance().setCustomKey("debug", true)
                Timber.plant(object : Timber.DebugTree() {
                    override fun createStackElementTag(element: StackTraceElement): String? {
                        return super.createStackElementTag(element) + "_MMF_APP"
                    }
                })
            }
        }

        fun trackEvent(event: String, logMessage: String? = null, bundle: Bundle? = null) {
            Timber.i(logMessage ?: event)
            firebaseAnalytics.logEvent(event, bundle)
        }

        fun trackError(throwable: Throwable, logMessage: String? = null) {
            Timber.e(logMessage ?: "Error: $throwable")
            firebaseCrashlytics.recordException(throwable)
        }

        fun trackLog(log: String) {
            Timber.i(log)
            firebaseCrashlytics.log(log)
        }
    }
}