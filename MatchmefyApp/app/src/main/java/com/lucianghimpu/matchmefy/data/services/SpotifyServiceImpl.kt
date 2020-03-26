package com.lucianghimpu.matchmefy.data.services

import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.networking.SpotifyRetrofitServiceFactory
import com.lucianghimpu.matchmefy.data.networking.retrofitInterfaces.SpotifyApiService

class SpotifyServiceImpl(spotifyRetrofitServiceFactory: SpotifyRetrofitServiceFactory) : SpotifyService {

    private var spotifyApiService : SpotifyApiService = spotifyRetrofitServiceFactory.create(
        SpotifyApiService::class.java)

    override suspend fun getUserProfile(): User {
        return spotifyApiService.getCurrentUserProfile()
    }
}