package com.lucianghimpu.matchmefy.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.services.MatchmefyService
import com.lucianghimpu.matchmefy.data.services.SpotifyService
import com.lucianghimpu.matchmefy.services.EncryptedSharedPreferencesServiceImpl
import com.lucianghimpu.matchmefy.services.SpotifyAuthService
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NavigationDirections.LOGIN_TO_WELCOME
import com.lucianghimpu.matchmefy.utilities.Preferences.SPOTIFY_TOKEN
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.*

/**
 * Assigned to the [MainActivity]
 * Used to share specific data to all Fragment's ViewModels
 * Also used to handle android scenarios like OnActivityResult
 * being caught in the Activity but the handling should be done in a Fragment
 */
class SharedViewModel(
    private val spotifyAuthService: SpotifyAuthService,
    private val spotifyService : SpotifyService,
    private val matchmefyService: MatchmefyService,
    private val encryptedSharedPreferencesServiceImpl: EncryptedSharedPreferencesServiceImpl
) : BaseViewModel() {

    var userProfile = MutableLiveData<User>()
    lateinit var userArtists: List<Artist>
    lateinit var userTracks: List<Track>

    fun onSpotifyAuthResponse(response: AuthorizationResponse) {

        val token = spotifyAuthService.onAuthResponse(response)

        Log.d(LOG_TAG, "Token: ${token}")
        encryptedSharedPreferencesServiceImpl.addPreference(SPOTIFY_TOKEN, token)

        getInitalData()
    }

    private suspend fun getData() : Triple<User, List<Artist>, List<Track>> {
        return coroutineScope {
            val user = async { spotifyService.getUserProfile() }
            val artists = async { spotifyService.getTopArtists() }
            val tracks = async { spotifyService.getTopTracks() }

            Triple(user.await(), artists.await(), tracks.await())
        }
    }

    fun getInitalData() {
        viewModelScope.launch {
            try {

                val data = withContext(Dispatchers.IO) {
                    getData()
                }

                userProfile.value = data.first
                Log.i(LOG_TAG, "Fetched profile for: ${userProfile.value!!.display_name}")

                userArtists = data.second
                Log.i(LOG_TAG, "Fetched top artists, with count: ${userArtists.size} and top artist: ${userArtists[0].name}")

                userTracks = data.third
                Log.i(LOG_TAG, "Fetched top tracks, with count: ${userTracks.size} and top track: ${userTracks[0].name}")

                withContext(Dispatchers.IO) {
                    matchmefyService.getUserData(userProfile.value!!.id)
                }

                Log.i(LOG_TAG, "Added user data to Matchmefy API")
                navigate(LOGIN_TO_WELCOME)
                
            } catch (ex: Exception) {
                Log.e(LOG_TAG, ex.toString())
            }
        }
    }


}