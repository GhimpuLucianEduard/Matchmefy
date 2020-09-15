package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

data class CreateUserRequest(
    val userData: CompleteUserData,
    val spotifyToken: String
)
