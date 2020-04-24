package com.lucianghimpu.matchmefy.data.services

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult

interface MatchmefyService {
    suspend fun getUserData(id: String) : CompleteUserData
    suspend fun getSearchUsers(searchQuery: String, limit: Int, offset: Int) : SearchUsersResult
}