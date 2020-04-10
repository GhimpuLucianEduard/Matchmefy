package com.lucianghimpu.matchmefy.data.services

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData

interface MatchmefyService {
    suspend fun getUserData(id: String) : CompleteUserData
}