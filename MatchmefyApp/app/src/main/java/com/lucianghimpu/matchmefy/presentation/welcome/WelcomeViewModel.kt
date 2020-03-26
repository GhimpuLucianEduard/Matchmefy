package com.lucianghimpu.matchmefy.presentation.welcome

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.data.services.SpotifyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class WelcomeViewModel(private val spotifyService : SpotifyService) : BaseViewModel() {
    // TODO: Implement the ViewModel

    fun getUserProfile() {
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    spotifyService.getUserProfile()
                }
                Log.i(LOG_TAG, user.display_name)
            } catch (ex: Exception) {
                Log.e(LOG_TAG, ex.toString())
            }
        }
    }
}
