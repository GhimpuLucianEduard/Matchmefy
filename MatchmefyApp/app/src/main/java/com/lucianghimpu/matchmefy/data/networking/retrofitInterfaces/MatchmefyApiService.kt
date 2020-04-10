package com.lucianghimpu.matchmefy.data.networking.retrofitInterfaces

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchmefyApiService {
    @GET("/{id}")
    suspend fun getUserData(@Path("id") id: String) : CompleteUserData
}