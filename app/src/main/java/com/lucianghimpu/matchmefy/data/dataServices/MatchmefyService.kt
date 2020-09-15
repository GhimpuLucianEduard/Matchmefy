package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CreateUserRequest
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CreateUserResponse
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult

interface MatchmefyService {
    suspend fun postUserData(createUserRequest: CreateUserRequest): CreateUserResponse

    suspend fun getRandomUser(): User

    // N.B. the API supports pagination but for the initial release
    // pagination is not used in the app so we just fetch 50 entries on each search
    suspend fun getSearchUsers(searchQuery: String,
                               limit: Int = 49,
                               offset: Int = 0) : SearchUsersResult

    suspend fun matchUsers(user: String): MatchResult

    suspend fun loadInitialMatches(): List<MatchResult>
    fun initialMatchesLoaded() : Boolean
    fun getMatches(filter: String): List<MatchResult>

    suspend fun deleteUserData(userId: String): Any
    fun deleteAll()
}