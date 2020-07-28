package com.lucianghimpu.matchmefy.appServices

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.SPOTIFY_ACCESS_TOKEN_KEY
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.SPOTIFY_REFRESH_TOKEN_KEY
import com.lucianghimpu.matchmefy.utilities.SpotifyAuthConstants
import net.openid.appauth.*

class AppAuthService(
    private val context: Context,
    private val encryptedSharedPreferencesService: EncryptedSharedPreferencesService
) {

    companion object {
        private const val AUTH_LOG_TAG: String =  "${LOG_TAG}_APP_AUTH"
    }

    private var authorizationResponse: AuthorizationResponse? = null

    private var serviceConfig: AuthorizationServiceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(SpotifyAuthConstants.AUTH_ENDPOINT),
        Uri.parse(SpotifyAuthConstants.TOKEN_ENDPOINT)
    )

    private var _authService: AuthorizationService = AuthorizationService(context)

    fun sendAuthCodeRequest(activity: Activity) {
        val builder: AuthorizationRequest.Builder = AuthorizationRequest.Builder(
            serviceConfig,
            SpotifyAuthConstants.CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse(SpotifyAuthConstants.CALLBACK_URL)
        )

        val authRequest = builder
            .setScopes(
                SpotifyAuthConstants.USER_READ_EMAIL_SCOPE,
                SpotifyAuthConstants.USER_TOP_READ_SCOPE,
                SpotifyAuthConstants.PLAYLIST_MODIFY_PRIVATE_SCOPE,
                SpotifyAuthConstants.PLAYLIST_MODIFY_PUBLIC_SCOPE)
            .build()

        val authIntent = _authService.getAuthorizationRequestIntent(authRequest)

        Log.i(AUTH_LOG_TAG, "Sending auth code request")
        activity.startActivityForResult(authIntent, SpotifyAuthConstants.ACTIVITY_REQUEST_CODE)
    }

    fun onAuthCodeResponse(data: Intent?) {
        authorizationResponse = AuthorizationResponse.fromIntent(data!!)
        Log.i(AUTH_LOG_TAG, "Auth code request received")
    }

    fun sendTokenRequest(callback: TokenReceivedCallback) {
        if (authorizationResponse == null) {
            Log.e(AUTH_LOG_TAG, "sendTokenRequest() called with authorizationResponse null")
            throw Exception("No auth code found, please make sure to first call sendAuthCodeRequest()")
        }

        val authorizationService = AuthorizationService(context)

        Log.i(AUTH_LOG_TAG, "Send token request")
        authorizationService.performTokenRequest(authorizationResponse!!.createTokenExchangeRequest()) { response, ex ->

            if (response != null) {
                Log.i(AUTH_LOG_TAG, "Saving tokens in preferences")
                // save access_token and refresh_token
                encryptedSharedPreferencesService.addString(
                    SPOTIFY_ACCESS_TOKEN_KEY,
                    response.accessToken!!,
                    true)

                encryptedSharedPreferencesService.addString(
                    SPOTIFY_REFRESH_TOKEN_KEY,
                    response.refreshToken!!,
                    true)

                // token fetched and stored
                callback.onSuccess()
            }
            else {
                callback.onError(ex!!)
            }
        }
    }

    interface TokenReceivedCallback {
        fun onSuccess()
        fun onError(ex: Exception)
    }
}