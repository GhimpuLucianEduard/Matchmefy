package com.lucianghimpu.matchmefy.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.lucianghimpu.matchmefy.BuildConfig
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAuthService
import com.lucianghimpu.matchmefy.utilities.EventObserver
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NavigationCommand
import com.lucianghimpu.matchmefy.utilities.SpotifyAuthConstants
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.AbstractCrashesListener
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog
import com.microsoft.appcenter.crashes.model.ErrorReport
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity(), AppAuthService.TokenReceivedCallback {

    private val authService: AppAuthService by inject()
    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAppCenter()

        // The shared view model does not have a BaseFragment associated
        // So we need to observer navigation changes in the MainActivity
        mainActivityViewModel.navigationEvent.observe(this, EventObserver {
            when (it) {
                is NavigationCommand.To -> Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(it.directions)
                is NavigationCommand.Back -> Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigateUp()
            }
        })

        setupNavigation()
    }

    fun setBottomNavigationBarVisibility(visibility: Int = View.GONE) {
        bottomNavigationBar.visibility = visibility
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SpotifyAuthConstants.ACTIVITY_REQUEST_CODE) {
            authService.onAuthCodeResponse(data)
            authService.sendTokenRequest(this)
        }
    }

    private fun initAppCenter() {
        val customListener: AbstractCrashesListener = object : AbstractCrashesListener() {
            override fun getErrorAttachments(report: ErrorReport?): MutableIterable<ErrorAttachmentLog> {
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

                return mutableListOf(ErrorAttachmentLog.attachmentWithText(logText, "log.txt"))
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

    private fun setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationBar.setupWithNavController(navController)
    }

    override fun onSuccess() {
        Log.i(LOG_TAG, "Token fetched")
        mainActivityViewModel.onAuthCompleted()
    }

    override fun onError(ex: Exception) {
        // TODO: handle error
        Log.e(LOG_TAG, "Error fetching token, cause: $ex")
    }
}
