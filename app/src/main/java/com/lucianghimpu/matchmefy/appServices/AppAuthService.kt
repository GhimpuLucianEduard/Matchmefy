package com.lucianghimpu.matchmefy.appServices

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import com.lucianghimpu.matchmefy.utilities.SpotifyAuthConstants
import net.openid.appauth.*
import org.json.JSONException
import java.util.concurrent.locks.ReentrantLock


/**
 * Implementation for [AuthService] using AppAuth
 *
 * Responsible for securely storing the tokens
 */
class AppAuthService(
    private val context: Context,
    private val encryptedSharedPreferencesService: EncryptedSharedPreferencesService
): AuthService {

    companion object {
        private const val AUTH_LOG_TAG: String =  "${LOG_TAG}_APP_AUTH"
    }

    private var authorizationResponse: AuthorizationResponse? = null
    private var preferencesLock: ReentrantLock = ReentrantLock()

    private var serviceConfig: AuthorizationServiceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(SpotifyAuthConstants.AUTH_ENDPOINT),
        Uri.parse(SpotifyAuthConstants.TOKEN_ENDPOINT)
    )

    override fun sendAuthCodeRequest(activity: Activity) {
        val builder: AuthorizationRequest.Builder = AuthorizationRequest.Builder(
            serviceConfig,
            SpotifyAuthConstants.CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse(SpotifyAuthConstants.CALLBACK_URL)
        )

        val authRequest = builder
            .setScopes(SpotifyAuthConstants.USER_READ_EMAIL_SCOPE, SpotifyAuthConstants.USER_TOP_READ_SCOPE)
            .build()

        val authService = AuthorizationService(context)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)

        Log.i(AUTH_LOG_TAG, "Sending auth code request")
        activity.startActivityForResult(authIntent, SpotifyAuthConstants.ACTIVITY_REQUEST_CODE)
    }

    override fun onAuthCodeResponse(data: Intent?) {
        authorizationResponse = AuthorizationResponse.fromIntent(data!!)
        val ex = AuthorizationException.fromIntent(data!!)
        val authState = AuthState().apply {
            update(authorizationResponse, ex)
        }
        writeState(authState)
        Log.i(AUTH_LOG_TAG, "Auth code request received")
    }

    override fun sendTokenRequest(callback: AuthService.TokenReceivedCallback) {
        if (authorizationResponse == null) {
            Log.e(AUTH_LOG_TAG, "sendTokenRequest() called with authorizationResponse null")
            throw Exception("No auth code found, please make sure to first call sendAuthCodeRequest()")
        }

        val authorizationService = AuthorizationService(context)

        Log.i(AUTH_LOG_TAG, "Send token request")
        authorizationService.performTokenRequest(authorizationResponse!!.createTokenExchangeRequest()) { response, ex ->

            if (response != null) {
                val authState = readState()
                authState.update(response, ex)
                writeState(authState)

                // token fetched and stored
                callback.onSuccess()
            }
            else {
                callback.onError(ex!!)
            }
        }
    }

    override fun getToken(): String? {
        return readState().accessToken
    }

    private fun readState(): AuthState {

        val stateJson = encryptedSharedPreferencesService.getPreference(
            PreferencesConstants.APP_AUTH_STATE_KEY,
            String.empty
        )
        return try {
            AuthState.jsonDeserialize(stateJson)
        } catch (ex: JSONException) {
            Log.e(AUTH_LOG_TAG, "Failed to deserialize stored auth state, returning new state")
            AuthState()
        }
    }

    private fun writeState(state: AuthState) {
        encryptedSharedPreferencesService.addPreference(PreferencesConstants.APP_AUTH_STATE_KEY,
            state.jsonSerializeString(), true)
    }
}