package com.lucianghimpu.matchmefy.presentation.services

import android.util.Log
import com.lucianghimpu.matchmefy.utilities.SpotifyCredentials
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class SpotifyAuthService {

    val SPOTIFY_AUTH_TAG = "SPOTIFY_AUTH"

    val AUTH_TOKEN_REQUEST_CODE = 508

    var accessToken: String? = null

    fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest {
        return AuthorizationRequest.Builder(SpotifyCredentials.CLIENT_ID, type, "http://localhost:8888/callback")
            .setShowDialog(true)
            .setScopes(arrayOf("user-read-email"))
            .build()
    }

    fun onAuthResponse(response: AuthorizationResponse)
    {
        when (response.type) {

            AuthorizationResponse.Type.CODE ->
                Log.i(SPOTIFY_AUTH_TAG, "Code response")

            AuthorizationResponse.Type.TOKEN -> {
                accessToken = response.accessToken
                Log.i(SPOTIFY_AUTH_TAG, "AccesToken retrieved successfully")
            }

            AuthorizationResponse.Type.ERROR -> {
                Log.e(SPOTIFY_AUTH_TAG, "Error retrieving the AccesToken:")
                Log.e(SPOTIFY_AUTH_TAG, response.error)
            }

            AuthorizationResponse.Type.EMPTY ->
                Log.i(SPOTIFY_AUTH_TAG, "Empty response ${response.error}")

            AuthorizationResponse.Type.UNKNOWN ->
                Log.i(SPOTIFY_AUTH_TAG, "Unknown response")
        }
    }
}