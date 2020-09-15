package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CreateUserRequest
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CreateUserResponse
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyApiService
import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyRetrofitServiceFactory

class MatchmefyServiceImpl(
    matchmefyRetrofitServiceFactory: MatchmefyRetrofitServiceFactory
) : MatchmefyService {

    private var matchmefyApiService : MatchmefyApiService = matchmefyRetrofitServiceFactory.create(
        MatchmefyApiService::class.java)

    private var userMatches: ArrayList<MatchResult> = arrayListOf()
    private var initialMatchesLoaded: Boolean = false

    override suspend fun postUserData(createUserRequest: CreateUserRequest): CreateUserResponse {
        return matchmefyApiService.postUserData(createUserRequest)
    }

    override suspend fun getRandomUser(): User {
        return matchmefyApiService.getRandomUser()
    }

    override suspend fun getSearchUsers(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): SearchUsersResult {
        return matchmefyApiService.getSearchUsers(searchQuery, limit, offset)
    }

    override suspend fun matchUsers(user: String) : MatchResult {
        val newMatch = matchmefyApiService.matchUsers(user)
        val oldMatchIndex = userMatches.indexOfFirst { m -> m._id == newMatch._id }
        if (oldMatchIndex == -1) {
            userMatches.add(newMatch)
        } else {
            userMatches[oldMatchIndex] = newMatch
        }
        return newMatch
    }

    override suspend fun loadInitialMatches(): List<MatchResult> {
        userMatches = ArrayList(matchmefyApiService.getMatches())
        initialMatchesLoaded = true
        return userMatches.sortedByDescending {
            it.matchingScore.toFloat()
        }
    }

    override fun initialMatchesLoaded(): Boolean = initialMatchesLoaded

    override fun getMatches(filter: String) : List<MatchResult> {

        if (!initialMatchesLoaded) {
            throw Exception("getMatches called before loadInitialMatches")
        }

        if (filter.isNotEmpty()) {
            return userMatches
                .filter {
                    match -> match.matchingUser.display_name.contains(filter) }
                .sortedByDescending {
                    it.matchingScore.toFloat()
                }
        }

        return userMatches.sortedByDescending {
            it.matchingScore.toFloat()
        }
    }

    override suspend fun deleteUserData(userId: String): Any {
        return matchmefyApiService.deleteUserData()
    }

    override fun deleteAll() {
        initialMatchesLoaded = false
        userMatches.clear()
    }
}