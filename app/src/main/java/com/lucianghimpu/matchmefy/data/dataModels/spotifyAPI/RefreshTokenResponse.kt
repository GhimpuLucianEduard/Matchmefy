package com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI

data class RefreshTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String
)