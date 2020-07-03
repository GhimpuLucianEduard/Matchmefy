package com.lucianghimpu.matchmefy.appServices

import android.app.Activity
import android.content.Intent

interface AuthService {

    fun sendAuthCodeRequest(activity: Activity)
    fun onAuthCodeResponse(data: Intent?)
    fun sendTokenRequest(callback: TokenReceivedCallback)
    fun getToken(): String?

    interface TokenReceivedCallback {
        fun onSuccess()
        fun onError(ex: Exception)
    }
}