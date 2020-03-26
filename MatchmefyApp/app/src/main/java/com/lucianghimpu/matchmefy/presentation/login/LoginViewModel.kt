package com.lucianghimpu.matchmefy.presentation.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.data.services.SpotifyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.LogConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginViewModel(
    private val spotifyService : SpotifyService
) : BaseViewModel() {



    fun getUserProfile() {
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    spotifyService.getUserProfile()
                }
                Log.i(LogConstants.LOG_TAG, user.display_name)
            } catch (ex: Exception) {
                Log.e(LogConstants.LOG_TAG, ex.toString())
            }
        }
    }
}
