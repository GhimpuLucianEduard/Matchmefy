package com.lucianghimpu.matchmefy.data.networking.retrofitInterfaces

import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.TopArtistsReponse
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.TopTracksResponse
import com.lucianghimpu.matchmefy.data.dataModels.User
import retrofit2.http.GET

interface SpotifyApiService {

    @GET("/v1/me")
    suspend fun getCurrentUserProfile() : User

    @GET("/v1/me/top/artists?time_range=long_term&limit=50")
    suspend fun getTopArtists() : TopArtistsReponse

    @GET("/v1/me/top/tracks?time_range=long_term&limit=50")
    suspend fun getTopTracks() : TopTracksResponse
}