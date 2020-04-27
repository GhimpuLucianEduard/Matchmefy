package com.lucianghimpu.matchmefy.utilities

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.presentation.login.LoginFragment
import com.lucianghimpu.matchmefy.presentation.login.LoginFragmentDirections

object LogConstants {
    const val LOG_TAG = "MMF_APP"
}

object SpotifyCredentials {
    const val CLIENT_ID = "e66d9e480684403c8df421a72a706c48"
}

object Preferences {
    const val PREFERENCES_FILE = "mmf_preferences"
    const val SPOTIFY_TOKEN = "spotify_token"
}

object ApiConstants {
    const val SPOTIFY_API_BASE_URL = "https://api.spotify.com"
    const val MATCHMEFY_API_BASE_URL = "https://matchmefy-api.herokuapp.com"

    const val DEFAULT_SEARCH_LIMIT = 5
}

object NavigationDirections {
    val LOGIN_TO_WELCOME = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
}