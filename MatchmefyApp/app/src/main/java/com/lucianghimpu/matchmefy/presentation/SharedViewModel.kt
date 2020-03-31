package com.lucianghimpu.matchmefy.presentation

import android.text.Layout
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.services.SpotifyService
import com.lucianghimpu.matchmefy.presentation.login.LoginFragment
import com.lucianghimpu.matchmefy.services.EncryptedSharedPreferencesServiceImpl
import com.lucianghimpu.matchmefy.services.SpotifyAuthService
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.LogConstants
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NavigationDirections.LOGIN_TO_WELCOME
import com.lucianghimpu.matchmefy.utilities.Preferences.SPOTIFY_TOKEN
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Assigned to the [MainActivity]
 * Used to share specific data to all Fragment's ViewModels
 * Also used to handle android scenarios like OnActivityResult
 * being caught in the Activity but the handling should be done in a Fragment
 */
class SharedViewModel(
    private val spotifyAuthService: SpotifyAuthService,
    private val spotifyService : SpotifyService,
    private val encryptedSharedPreferencesServiceImpl: EncryptedSharedPreferencesServiceImpl
) : BaseViewModel() {

    var userProfile = MutableLiveData<User>()

    fun onSpotifyAuthResponse(response: AuthorizationResponse) {

        val token = spotifyAuthService.onAuthResponse(response)

        Log.d(LOG_TAG, "Token: ${token}")
        encryptedSharedPreferencesServiceImpl.addPreference(SPOTIFY_TOKEN, token)

        getUserProfile()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            try {
                userProfile.value = withContext(Dispatchers.IO) {
                    spotifyService.getUserProfile()
                }
                var ce = userProfile.value
                Log.i(LOG_TAG, "Fetched user profile for: ${userProfile.value!!.display_name}")
                navigate(LOGIN_TO_WELCOME)
            } catch (ex: Exception) {
                Log.e(LOG_TAG, ex.toString())
            }
        }
    }
}