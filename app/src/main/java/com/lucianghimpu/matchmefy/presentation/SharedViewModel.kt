package com.lucianghimpu.matchmefy.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.data.dataServices.SpotifyService
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NavigationDirections.LOGIN_TO_WELCOME
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.USER_PROFILE_KEY
import kotlinx.coroutines.*

/**
 * Assigned to the [MainActivity]
 * Used to share specific data to all Fragment's ViewModels
 * Also used to handle android scenarios like OnActivityResult
 * being caught in the Activity but the handling should be done in a Fragment
 */
class SharedViewModel(
    private val spotifyService : SpotifyService,
    private val matchmefyService: MatchmefyService,
    private val encryptedSharedPreferencesService: EncryptedSharedPreferencesService
) : BaseViewModel() {

    var userProfile = MutableLiveData<User>()

    init {
        // get user profile from preferences
        userProfile.value = encryptedSharedPreferencesService.getPreference(USER_PROFILE_KEY, User::class)
    }

    fun onAuthResponse() {
        getInitialData()
    }

    private suspend fun getData() : Triple<User, List<Artist>, List<Track>> {
        return coroutineScope {
            val user = async { spotifyService.getUserProfile() }
            val artists = async { spotifyService.getTopArtists() }
            val tracks = async { spotifyService.getTopTracks() }

            Triple(user.await(), artists.await(), tracks.await())
        }
    }

    private fun getInitialData() {
        viewModelScope.launch {
            try {

                val data = withContext(Dispatchers.IO) {
                    getData()
                }

                userProfile.value = data.first

                // save user in shared preferences
                encryptedSharedPreferencesService.addPreference(USER_PROFILE_KEY, data.first)

                Log.i(LOG_TAG, "Fetched profile for: ${userProfile.value!!.display_name}")

                Log.i(LOG_TAG, "Fetched top artists, with count: ${data.second.size} and top artist: ${data.second[0].name}")

                Log.i(LOG_TAG, "Fetched top tracks, with count: ${data.third.size} and top track: ${data.third[0].name}")

                withContext(Dispatchers.IO) {
                    matchmefyService.postUserData(CompleteUserData(data.first, data.second, data.third))
                }

                Log.i(LOG_TAG, "Added user data to Matchmefy API")
                navigate(LOGIN_TO_WELCOME)
                
            } catch (ex: Exception) {
                Log.e(LOG_TAG, ex.toString())
            }
        }
    }
}