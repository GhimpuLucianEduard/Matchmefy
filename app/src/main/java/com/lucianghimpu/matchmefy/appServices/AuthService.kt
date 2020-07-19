package com.lucianghimpu.matchmefy.appServices

import android.app.Activity
import android.content.Intent

interface AuthService {

    fun sendAuthCodeRequest(activity: Activity)
    fun onAuthCodeResponse(data: Intent?)
    fun sendTokenRequest(callback: TokenReceivedCallback)

    /**
     * Get a access_token for the current signed user
     * @return the access_token if there is a signed in user or empty string
     * if there is no signed in user
     */
    fun getToken(): String?

    interface TokenReceivedCallback {
        fun onSuccess()
        fun onError(ex: Exception)
    }
}