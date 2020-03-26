package com.lucianghimpu.matchmefy.data.services

import com.lucianghimpu.matchmefy.data.dataModels.User

interface SpotifyService {
    suspend fun getUserProfile() : User
}