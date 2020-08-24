package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.CreatePlaylistRequest
import com.lucianghimpu.matchmefy.data.dataModels.Playlist
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.EditPlaylistRequest
import com.lucianghimpu.matchmefy.data.networking.spotify.SpotifyApiService
import com.lucianghimpu.matchmefy.data.networking.spotify.SpotifyRetrofitServiceFactory
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants

class SpotifyServiceImpl(
    spotifyRetrofitServiceFactory: SpotifyRetrofitServiceFactory,
    private val preferencesService: PreferencesService
) : SpotifyService {
    private var spotifyApiService : SpotifyApiService = spotifyRetrofitServiceFactory.create(
        SpotifyApiService::class.java)

    override suspend fun getUserProfile(): User {
        return spotifyApiService.getCurrentUserProfile()
    }

    override suspend fun getTopArtists(): List<Artist> {
        return spotifyApiService.getTopArtists().items
    }

    override suspend fun getTopTracks(): List<Track> {
        return spotifyApiService.getTopTracks().items
    }

    override suspend fun createPlaylist(playlistRequest: CreatePlaylistRequest): Playlist {
        val userProfile = preferencesService.getObject(
            PreferencesConstants.USER_PROFILE_KEY, User::class)

        if (userProfile != null) {
            return spotifyApiService.createPlaylist(userProfile.id, playlistRequest)
        } else {
            throw Exception("No valid user saved in order to send Create Playlist Request")
        }
    }

    override suspend fun editPlaylist(
        playlistId: String,
        editPlaylistRequest: EditPlaylistRequest
    ): Any {
        return spotifyApiService.editPlaylist(playlistId, editPlaylistRequest)
    }
}