package com.lucianghimpu.matchmefy.data.services

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.networking.MatchmefyRetrofitServiceFactory
import com.lucianghimpu.matchmefy.data.networking.retrofitInterfaces.MatchmefyApiService

class MatchmefyServiceImpl(matchmefyRetrofitServiceFactory: MatchmefyRetrofitServiceFactory) : MatchmefyService {

    private var matchmefyApiService : MatchmefyApiService = matchmefyRetrofitServiceFactory.create(
        MatchmefyApiService::class.java)

    override suspend fun getUserData(id: String): CompleteUserData {
        return matchmefyApiService.getUserData(id)
    }
}