package com.lucianghimpu.matchmefy.appServices.Auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.SPOTIFY_ACCESS_TOKEN_KEY
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.SPOTIFY_REFRESH_TOKEN_KEY
import com.lucianghimpu.matchmefy.utilities.SpotifyAuthConstants
import net.openid.appauth.*

class AppAuthService(
    private val context: Context,
    private val preferencesService: PreferencesService
) {

    private var authListener: AuthListener? = null
    private var authorizationResponse: AuthorizationResponse? = null

    private var serviceConfig: AuthorizationServiceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(SpotifyAuthConstants.AUTH_ENDPOINT),
        Uri.parse(SpotifyAuthConstants.TOKEN_ENDPOINT)
    )

    private var _authService: AuthorizationService = AuthorizationService(context)

    fun setAuthListener(authListener: AuthListener) {
        this.authListener = authListener
    }

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

        AppAnalytics.trackLog("Sending Auth Code request")
        activity.startActivityForResult(authIntent, SpotifyAuthConstants.ACTIVITY_REQUEST_CODE)
    }

    fun onAuthCodeResponse(data: Intent?): Boolean {
        authorizationResponse = AuthorizationResponse.fromIntent(data!!)
        if (authorizationResponse == null) {
            AppAnalytics.trackLog("Auth Code response is null, cancelling operation")
            authListener?.onCancel()
            return false
        }
        AppAnalytics.trackLog("Auth Code valid")
        return true
    }

    fun sendTokenRequest() {
        if (authorizationResponse == null) {
            throw Exception("No auth code found, please make sure to first call sendAuthCodeRequest()")
        }

        val authorizationService = AuthorizationService(context)

        AppAnalytics.trackLog(  "Sending Auth Token request")
        authorizationService.performTokenRequest(authorizationResponse!!.createTokenExchangeRequest()) { response, ex ->

            if (response != null) {
                AppAnalytics.trackLog("Saving tokens in preferences")
                // save access_token and refresh_token
                preferencesService.addString(
                    SPOTIFY_ACCESS_TOKEN_KEY,
                    response.accessToken!!,
                    true)

                preferencesService.addString(
                    SPOTIFY_REFRESH_TOKEN_KEY,
                    response.refreshToken!!,
                    true)

                // token fetched and stored
                authListener?.onSuccess()
            }
            else {
                authListener?.onError(ex!!)
            }
        }
    }
}