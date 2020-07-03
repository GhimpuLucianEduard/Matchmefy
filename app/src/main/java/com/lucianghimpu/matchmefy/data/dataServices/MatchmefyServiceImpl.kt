package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import com.lucianghimpu.matchmefy.data.networking.MatchmefyRetrofitServiceFactory
import com.lucianghimpu.matchmefy.data.networking.retrofitInterfaces.MatchmefyApiService

class MatchmefyServiceImpl(matchmefyRetrofitServiceFactory: MatchmefyRetrofitServiceFactory) : MatchmefyService {

    private var matchmefyApiService : MatchmefyApiService = matchmefyRetrofitServiceFactory.create(
        MatchmefyApiService::class.java)

    override suspend fun postUserData(completeUserData: CompleteUserData) {
        return matchmefyApiService.postUserData(completeUserData)
    }

    override suspend fun getSearchUsers(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): SearchUsersResult {
        return matchmefyApiService.getSearchUsers(searchQuery, limit, offset)
    }

    override suspend fun matchUsers(firstUser: String, secondUser: String): MatchResult {
        return matchmefyApiService.matchUsers(firstUser, secondUser)
    }
}