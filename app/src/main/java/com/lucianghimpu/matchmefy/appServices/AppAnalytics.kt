package com.lucianghimpu.matchmefy.appServices

import android.app.Application
import android.util.Log
import com.lucianghimpu.matchmefy.BuildConfig
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.AbstractCrashesListener
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog
import com.microsoft.appcenter.crashes.model.ErrorReport
import java.io.BufferedReader
import java.io.InputStreamReader

class AppAnalytics {
    companion object {
        fun initAppCenter(application: Application) {
            val customListener: AbstractCrashesListener = object : AbstractCrashesListener() {
                override fun getErrorAttachments(report: ErrorReport?): MutableIterable<ErrorAttachmentLog> {
                    return mutableListOf(getLogText())
                }
            }

            Crashes.setListener(customListener)

            if (BuildConfig.DEBUG) {
                AppCenter.start(
                    application, "93422139-2b83-43c0-a01b-30f7bb81c5cd",
                    Analytics::class.java, Crashes::class.java
                )
                Log.i(LOG_TAG, "AppCenter init for DEBUG")
            } else {
                AppCenter.start(
                    application, "2ead82a3-3637-4348-82ca-d26fdc0f507a",
                    Analytics::class.java
                )
                Log.i(LOG_TAG, "AppCenter init for NON DEBUG")
            }
        }

        fun trackEvent(event: String, logMessage: String? = null) {
            Log.i(LOG_TAG, logMessage ?: event)
            Log.i(LOG_TAG, "Sending event $event to AppCenter")
            Analytics.trackEvent(event)
        }

        fun trackError(throwable: Throwable, logMessage: String? = null, attachLog: Boolean = false) {
            Log.e(LOG_TAG, logMessage ?: "Error: $throwable")
            Log.i(LOG_TAG, "Sending error $throwable AppCenter")
            val log = if (attachLog) mutableListOf(getLogText()) else null
            Crashes.trackError(throwable, null, log)
        }

        private fun getLogText(): ErrorAttachmentLog {
            var logText = "Cannot retrieve log content"
            try {
                val process =
                    Runtime.getRuntime().exec("logcat -d")
                val bufferedReader = BufferedReader(
                    InputStreamReader(process.inputStream)
                )
                val log = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    log.append(line + "\n")
                }
                logText = log.toString()
            } catch (ex: Exception) {
                Log.e(LOG_TAG, logText)
            }

            return ErrorAttachmentLog.attachmentWithText(logText, "log.txt")
        }
    }
}