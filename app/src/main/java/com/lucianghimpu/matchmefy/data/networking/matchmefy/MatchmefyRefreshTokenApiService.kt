package com.lucianghimpu.matchmefy.data.networking.matchmefy

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchmefyRefreshToken
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchmefyRefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchmefyRefreshTokenApiService {
    @POST("/users/refresh")
    fun refreshToken(@Body refreshToken: MatchmefyRefreshToken): Call<MatchmefyRefreshTokenResponse>
}