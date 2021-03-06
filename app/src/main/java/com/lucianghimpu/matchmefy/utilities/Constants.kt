package com.lucianghimpu.matchmefy.utilities

import com.lucianghimpu.matchmefy.BuildConfig

object DIConstants {
    const val SINGLE_BUTTON_DIALOG = "singleButtonDialog"
    const val DOUBLE_BUTTON_DIALOG = "doubleButtonDialog"
    const val LOADING_DIALOG = "loadingDialog"
    const val NO_CONNECTION_DIALOG = "noConnectionDialog"
    const val TERMS_AND_PRIVACY_DIALOG = "termsAndPrivacyDialog"
}

object SpotifyAuthConstants {
    const val CLIENT_ID = BuildConfig.spotify_client_id
    const val API_BASE_URL = "https://accounts.spotify.com"
    const val AUTH_ENDPOINT = "https://accounts.spotify.com/authorize"
    const val TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token"
    const val CALLBACK_URL = "com.lucianghimpu.matchmefy:/oauth2callback"
    const val USER_READ_EMAIL_SCOPE = "user-read-email"
    const val USER_TOP_READ_SCOPE = "user-top-read"
    const val PLAYLIST_MODIFY_PUBLIC_SCOPE = "playlist-modify-public"
    const val PLAYLIST_MODIFY_PRIVATE_SCOPE = "playlist-modify-private"

    const val ACTIVITY_REQUEST_CODE = 578
}

object PreferencesConstants {
    const val PREFERENCES_FILE_KEY = "mmf_preferences"
    const val SPOTIFY_ACCESS_TOKEN_KEY = "spotify_access_token"
    const val SPOTIFY_REFRESH_TOKEN_KEY = "spotify_refresh_token"
    const val MATCHMEFY_ACCESS_TOKEN_KEY = "matchmefy_access_token"
    const val MATCHMEFY_REFRESH_TOKEN_KEY = "matchmefy_refresh_token"
    const val USER_PROFILE_KEY = "user_profile"
}

object ApiConstants {
    const val SPOTIFY_API_BASE_URL = "https://api.spotify.com"
    const val MATCHMEFY_API_BASE_URL = BuildConfig.matchmefy_api_url
}

object WebConstants {
    const val MATCHMEFY_WEB = "https://www.matchmefy.com"
    const val MATCHMEFY_MOBILE_TERMS = "https://www.matchmefy.com/mobile-terms"
    const val MATCHMEFY_MOBILE_PRIVACY_POLICY = "https://www.matchmefy.com/mobile-privacy"
    const val MATCHMEFY_GITHUB = "https://github.com/GhimpuLucianEduard/Matchmefy"
}

object DialogTagsConstants {
    const val SINGLE_BUTTON_DIALOG_TAG = "singleButtonDialog"
    const val DOUBLE_BUTTON_DIALOG_TAG = "doubleButtonDialog"
    const val LOADING_DIALOG_TAG = "loadingDialog"
    const val NO_CONNECTION_DIALOG_TAG = "noConnectionDialog"
    const val TERMS_AND_PRIVACY_DIALOG_TAG = "termsAndPrivacyDialog"
}