package com.lucianghimpu.matchmefy.data.networking.spotify

import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.RefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotifyAuthApiService {
    @POST("/api/token")
    @FormUrlEncoded
    fun refreshToken(
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String) : Call<RefreshTokenResponse>
}