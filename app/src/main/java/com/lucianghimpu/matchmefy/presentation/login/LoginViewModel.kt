package com.lucianghimpu.matchmefy.presentation.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.Auth.AppAuthService
import com.lucianghimpu.matchmefy.appServices.Auth.AuthListener
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CreateUserRequest
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.data.dataServices.SpotifyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import kotlinx.coroutines.*
import timber.log.Timber

class LoginViewModel(
    application: Application,
    preferencesService: PreferencesService,
    private val appAuthService: AppAuthService,
    private val spotifyService : SpotifyService,
    private val matchmefyService: MatchmefyService
) : BaseViewModel(application, preferencesService), AuthListener {

    init {
        appAuthService.setAuthListener(this)
    }

    fun onSignInClicked() {
        _isBusy.value = true
    }

    override fun onSuccess() {
        AppAnalytics.trackEvent("auth_success")
        getInitialData()
    }

    override fun onError(ex: Exception) {
        AppAnalytics.trackEvent("auth_error")
        _isBusy.value = false
        handleError(ex)
    }

    override fun onCancel() {
        AppAnalytics.trackEvent("auth_cancel")
        _isBusy.value = false
    }

    private fun getInitialData() {
        viewModelScope.launch {
            try {

                val data = withContext(Dispatchers.IO) {
                    getData()
                }

                // save user in shared preferences
                preferencesService.addObject(PreferencesConstants.USER_PROFILE_KEY, data.first)

                Timber.d("Fetched profile for: ${data.first.display_name}")
                Timber.d("Fetched top artists, with count: ${data.second.size}}")
                Timber.d("Fetched top tracks, with count: ${data.third.size}}")

                AppAnalytics.trackLog("Fetched user data")

                val spotifyToken = preferencesService.getString(PreferencesConstants.SPOTIFY_ACCESS_TOKEN_KEY)

                val response = withContext(Dispatchers.IO) {
                    matchmefyService.postUserData(CreateUserRequest(CompleteUserData(data.first, data.second, data.third), spotifyToken))
                }

                preferencesService.addString(
                    PreferencesConstants.MATCHMEFY_ACCESS_TOKEN_KEY,
                    response.accessToken,
                    true)

                preferencesService.addString(
                    PreferencesConstants.MATCHMEFY_REFRESH_TOKEN_KEY,
                    response.refreshToken,
                    true)

                AppAnalytics.trackLog("Added user data to Matchmefy API")
                navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())

            } catch (ex: Exception) {
                preferencesService.deleteAll()
                handleError(ex)
            }
            finally {
                _isBusy.value = false
            }
        }
    }

    private suspend fun getData() : Triple<User, List<Artist>, List<Track>> {
        return coroutineScope {
            val user = async { spotifyService.getUserProfile() }
            val artists = async { spotifyService.getTopArtists() }
            val tracks = async { spotifyService.getTopTracks() }

            Triple(user.await(), artists.await(), tracks.await())
        }
    }
}