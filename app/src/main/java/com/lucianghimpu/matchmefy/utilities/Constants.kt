package com.lucianghimpu.matchmefy.utilities

import com.lucianghimpu.matchmefy.presentation.login.LoginFragmentDirections

object LogConstants {
    const val LOG_TAG = "MMF_APP"
}

object SpotifyAuthConstants {
    const val CLIENT_ID = "e66d9e480684403c8df421a72a706c48"
    const val AUTH_ENDPOINT = "https://accounts.spotify.com/authorize"
    const val TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token"
    const val CALLBACK_URL = "com.lucianghimpu.matchmefy:/oauth2callback"
    const val USER_READ_EMAIL_SCOPE = "user-read-email"
    const val USER_TOP_READ_SCOPE = "user-top-read"

    const val ACTIVITY_REQUEST_CODE = 578
}

object PreferencesConstants {
    const val PREFERENCES_FILE_KEY = "mmf_preferences"
    const val APP_AUTH_STATE_KEY = "spotify_token"
    const val USER_PROFILE_KEY = "user_profile"
}

object ApiConstants {
    const val SPOTIFY_API_BASE_URL = "https://api.spotify.com"
    const val MATCHMEFY_API_BASE_URL = "https://matchmefy-api.herokuapp.com"

    const val DEFAULT_SEARCH_LIMIT = 5
}

object NavigationDirections {
    val LOGIN_TO_WELCOME = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
}