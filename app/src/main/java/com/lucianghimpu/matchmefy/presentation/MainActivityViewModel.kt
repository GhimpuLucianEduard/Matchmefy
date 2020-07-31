package com.lucianghimpu.matchmefy.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.data.dataServices.SpotifyService
import com.lucianghimpu.matchmefy.presentation.login.LoginFragmentDirections
import com.lucianghimpu.matchmefy.presentation.search.SearchFragmentDirections
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.USER_PROFILE_KEY
import kotlinx.coroutines.*

/**
 * Assigned to the [MainActivity]
 * Ued to handle android scenarios like OnActivityResult
 * being caught in the Activity but the handling should be done in a Fragment
 */
class MainActivityViewModel(
    private val spotifyService : SpotifyService,
    private val matchmefyService: MatchmefyService,
    private val encryptedSharedPreferencesService: EncryptedSharedPreferencesService
) : BaseViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        // get user profile from preferences OR redirect to login
        _user.value = encryptedSharedPreferencesService.getObject(USER_PROFILE_KEY, User::class)
        if (_user.value == null) {
            navigate(SearchFragmentDirections.actionSearchFragmentToLoginFragment())
        }
    }

    /**
     * Called when the auth process has been completed successfully
     */
    fun onAuthCompleted() {
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

                _user.value = data.first

                // save user in shared preferences
                encryptedSharedPreferencesService.addObject(USER_PROFILE_KEY, data.first)

                Log.i(LOG_TAG, "Fetched profile for: ${_user.value!!.display_name}")

                Log.i(LOG_TAG, "Fetched top artists, with count: ${data.second.size} and top artist: ${data.second[0].name}")

                Log.i(LOG_TAG, "Fetched top tracks, with count: ${data.third.size} and top track: ${data.third[0].name}")

                withContext(Dispatchers.IO) {
                    matchmefyService.postUserData(CompleteUserData(data.first, data.second, data.third))
                }

                Log.i(LOG_TAG, "Added user data to Matchmefy API")
                navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
                
            } catch (ex: Exception) {
                Log.e(LOG_TAG, ex.toString())
            }
        }
    }
}