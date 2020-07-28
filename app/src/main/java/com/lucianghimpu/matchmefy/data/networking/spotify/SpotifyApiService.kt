package com.lucianghimpu.matchmefy.data.networking.spotify

import com.lucianghimpu.matchmefy.data.dataModels.Playlist
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SpotifyApiService {

    @GET("/v1/me")
    suspend fun getCurrentUserProfile() : User

    @GET("/v1/me/top/artists?time_range=long_term&limit=50")
    suspend fun getTopArtists() : TopArtistsReponse

    @GET("/v1/me/top/tracks?time_range=long_term&limit=50")
    suspend fun getTopTracks() : TopTracksResponse

    @POST("/v1/users/{userId}/playlists")
    suspend fun createPlaylist(@Path(value = "userId", encoded = true) userId: String,
                               @Body playlistRequest: CreatePlaylistRequest) : Playlist

    @POST("/v1/playlists/{playlistId}/tracks")
    suspend fun editPlaylist(@Path(value = "playlistId", encoded = true) playlistId: String,
                             @Body editPlaylistRequest: EditPlaylistRequest)
}