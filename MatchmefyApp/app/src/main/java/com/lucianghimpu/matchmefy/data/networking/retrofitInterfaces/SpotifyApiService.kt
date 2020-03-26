package com.lucianghimpu.matchmefy.data.networking.retrofitInterfaces

import com.lucianghimpu.matchmefy.data.dataModels.User
import retrofit2.http.GET

interface SpotifyApiService {

    @GET("/v1/me")
    suspend fun getCurrentUserProfile() : User
}