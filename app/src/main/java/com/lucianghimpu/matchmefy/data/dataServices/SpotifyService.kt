package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Playlist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.CreatePlaylistRequest
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.EditPlaylistRequest

interface SpotifyService {
    suspend fun getUserProfile() : User
    suspend fun getTopArtists() : List<Artist>
    suspend fun getTopTracks() : List<Track>
    suspend fun createPlaylist(playlistRequest: CreatePlaylistRequest) : Playlist
    suspend fun editPlaylist(playlistId: String, editPlaylistRequest: EditPlaylistRequest) : Any
}