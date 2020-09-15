package com.lucianghimpu.matchmefy.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.Auth.AppAuthService
import com.lucianghimpu.matchmefy.appServices.Connectivity.ConnectivityService
import com.lucianghimpu.matchmefy.presentation.dialogs.noConnection.NoConnectionDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.noConnection.NoConnectionDialogFragment
import com.lucianghimpu.matchmefy.utilities.DialogTagsConstants
import com.lucianghimpu.matchmefy.utilities.EventObserver
import com.lucianghimpu.matchmefy.utilities.NavigationCommand
import com.lucianghimpu.matchmefy.utilities.SpotifyAuthConstants
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val connectivityService: ConnectivityService by inject()
    private val authService: AppAuthService by inject()
    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    private lateinit var navController: NavController

    private var noConnectionDialogFragment: NoConnectionDialogFragment? = null

    private val navigationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        AppAnalytics.trackLog("Navigation change, destination: ${destination.label}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setTheme(R.style.AppTheme)
        initObservers()
        setupNavigation()
    }

    private fun initObservers() {
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

        mainActivityViewModel.connectivityChangedEvent.observe(this, EventObserver {
            if (it) {
                AppAnalytics.trackLog("Dismissing no connection dialog")
                noConnectionDialogFragment?.dismiss()
                noConnectionDialogFragment = null
            } else {
                NoConnectionDialogFragment(NoConnectionDialog()).also { dialogFragment ->
                    noConnectionDialogFragment = dialogFragment
                    AppAnalytics.trackLog("Showing no connection dialog")
                    dialogFragment.show(this.supportFragmentManager,
                        DialogTagsConstants.NO_CONNECTION_DIALOG_TAG
                    )
                }
            }
        })
    }

    fun setBottomNavigationBarVisibility(visibility: Int = View.GONE) {
        bottomNavigationBar.visibility = visibility
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SpotifyAuthConstants.ACTIVITY_REQUEST_CODE) {
            if (authService.onAuthCodeResponse(data)) {
                authService.sendTokenRequest()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(navigationListener)
        connectivityService.start()
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(navigationListener)
        connectivityService.stop()
        super.onPause()
    }

    private fun setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationBar.setupWithNavController(navController)
    }
}
