package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyApiService
import com.lucianghimpu.matchmefy.data.networking.matchmefy.MatchmefyRetrofitServiceFactory

class MatchmefyServiceImpl(matchmefyRetrofitServiceFactory: MatchmefyRetrofitServiceFactory) : MatchmefyService {

    private var matchmefyApiService : MatchmefyApiService = matchmefyRetrofitServiceFactory.create(
        MatchmefyApiService::class.java)

    private var userMatches: ArrayList<MatchResult> = arrayListOf()
    private var initialMatchesLoaded: Boolean = false

    override suspend fun postUserData(completeUserData: CompleteUserData) {
        return matchmefyApiService.postUserData(completeUserData)
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

    override suspend fun matchUsers(firstUser: String, secondUser: String) : MatchResult {
        val newMatch = matchmefyApiService.matchUsers(firstUser, secondUser)
        val oldMatchIndex = userMatches.indexOfFirst { m -> m._id == newMatch._id }
        if (oldMatchIndex == -1) {
            userMatches.add(newMatch)
        } else {
            userMatches[oldMatchIndex] = newMatch
        }
        return newMatch
    }

    override suspend fun loadInitialMatches(userId: String): List<MatchResult> {
        userMatches = ArrayList(matchmefyApiService.getMatches(userId))
        initialMatchesLoaded = true
        return userMatches.sortedByDescending {
            it.matchingScore.toFloat()
        }
    }

    override fun initialMatchesLoaded(): Boolean = initialMatchesLoaded

    override fun getMatches(userId: String, filter: String) : List<MatchResult> {

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
        return matchmefyApiService.deleteUserData(userId)
    }

    override fun deleteAll() {
        initialMatchesLoaded = false
        userMatches.clear()
    }
}