package com.lucianghimpu.matchmefy.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.services.SpotifyService
import com.lucianghimpu.matchmefy.presentation.login.LoginFragment
import com.lucianghimpu.matchmefy.services.EncryptedSharedPreferencesServiceImpl
import com.lucianghimpu.matchmefy.services.SpotifyAuthService
import com.lucianghimpu.matchmefy.utilities.EventObserver
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.Preferences.SPOTIFY_TOKEN
import com.spotify.sdk.android.auth.AuthorizationClient
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val spotifyAuthService: SpotifyAuthService by inject()
    private val sharedViewModel: SharedViewModel by inject()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // The shared view model does not have a BaseFragment associated
        // So we need to observer navigation changes in the MainActivity
        sharedViewModel.navigationEvent.observe(this, EventObserver {
            Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(it)
        })

//        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

//        // set bottom navigation
//        bottom_nav.setupWithNavController(navController)

//        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
//        navController = navHost!!.navController
//
//        val navInflater = navController.navInflater
//        val graph = navInflater.inflate(R.navigation.navigation_graph)
//
//        graph.startDestination = R.id.loginFragment

    }

    fun setBottomNavigationBarVisibility(visibility: Int = View.GONE) {
        bottomNavigationBar.visibility = visibility
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == spotifyAuthService.AUTH_TOKEN_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            sharedViewModel.onSpotifyAuthResponse(response)
        }
    }
}
