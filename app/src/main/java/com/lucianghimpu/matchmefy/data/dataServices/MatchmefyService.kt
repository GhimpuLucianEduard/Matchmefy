package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult

interface MatchmefyService {
    suspend fun postUserData(completeUserData: CompleteUserData)

    suspend fun getRandomUser(): User

    // N.B. the API supports pagination but for the initial release
    // pagination is not used in the app so we just fetch 50 entries on each search
    suspend fun getSearchUsers(searchQuery: String,
                               limit: Int = 49,
                               offset: Int = 0) : SearchUsersResult

    suspend fun matchUsers(firstUser: String, secondUser: String) : MatchResult

    suspend fun loadInitialMatches(userId: String) : List<MatchResult>
    fun initialMatchesLoaded() : Boolean
    fun getMatches(userId: String, filter: String) : List<MatchResult>

    suspend fun deleteUserData(userId: String): Any
}