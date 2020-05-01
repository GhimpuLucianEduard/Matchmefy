package com.lucianghimpu.matchmefy.data.services

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import retrofit2.Call

interface MatchmefyService {
    suspend fun getUserData(id: String) : CompleteUserData
    fun getSearchUsers(searchQuery: String, limit: Int, offset: Int) : Call<SearchUsersResult>
}