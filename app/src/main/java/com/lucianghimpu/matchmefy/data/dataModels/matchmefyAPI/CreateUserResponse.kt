package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

import com.lucianghimpu.matchmefy.data.dataModels.User

data class CreateUserResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)