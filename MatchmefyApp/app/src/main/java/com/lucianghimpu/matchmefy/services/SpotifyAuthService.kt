package com.lucianghimpu.matchmefy.services

import android.util.Log
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.SpotifyCredentials
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class SpotifyAuthService() {

    val SPOTIFY_AUTH_TAG = "${LOG_TAG}_AUTH"

    val AUTH_TOKEN_REQUEST_CODE = 508

    var accessToken: String? = null

    fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest {
        return AuthorizationRequest.Builder(SpotifyCredentials.CLIENT_ID, type, "http://localhost:8888/callback")
            .setShowDialog(true)
            .setScopes(arrayOf("user-read-email", "user-top-read"))
            .build()
    }

    fun onAuthResponse(response: AuthorizationResponse): String
    {
        when (response.type) {

            AuthorizationResponse.Type.CODE -> {
                Log.i(SPOTIFY_AUTH_TAG, "Code response")
                return String.empty
            }

            AuthorizationResponse.Type.TOKEN -> {
                Log.i(SPOTIFY_AUTH_TAG, "AccesToken retrieved successfully")
                return response.accessToken
            }

            AuthorizationResponse.Type.ERROR -> {
                Log.e(SPOTIFY_AUTH_TAG, "Error retrieving the AccesToken:")
                Log.e(SPOTIFY_AUTH_TAG, response.error)
                return String.empty
            }

            AuthorizationResponse.Type.EMPTY -> {
                Log.i(SPOTIFY_AUTH_TAG, "Empty response ${response.error}")
                return String.empty
            }

            AuthorizationResponse.Type.UNKNOWN -> {
                Log.i(SPOTIFY_AUTH_TAG, "Unknown response")
                return String.empty
            }

            else -> return String.empty
        }
    }
}