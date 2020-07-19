package com.lucianghimpu.matchmefy.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AuthService
import com.lucianghimpu.matchmefy.presentation.search.SearchFragmentDirections
import com.lucianghimpu.matchmefy.utilities.EventObserver
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NavigationCommand
import com.lucianghimpu.matchmefy.utilities.SpotifyAuthConstants
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(), AuthService.TokenReceivedCallback {

    private val authService: AuthService by inject()
    private val sharedViewModel: SharedViewModel by inject()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // The shared view model does not have a BaseFragment associated
        // So we need to observer navigation changes in the MainActivity
        sharedViewModel.navigationEvent.observe(this, EventObserver {
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

    private fun setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationBar.setupWithNavController(navController)
    }

    override fun onSuccess() {
        Log.i(LOG_TAG, "Token fetched")
        sharedViewModel.onAuthCompleted()
    }

    override fun onError(ex: Exception) {
        // TODO: handle error
        Log.e(LOG_TAG, "Error fetching token, cause: $ex")
    }
}
