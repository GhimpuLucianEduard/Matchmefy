package com.lucianghimpu.matchmefy.appServices.Auth

interface AuthListener {
    fun onSuccess()
    fun onError(ex: Exception)
    fun onCancel()
}