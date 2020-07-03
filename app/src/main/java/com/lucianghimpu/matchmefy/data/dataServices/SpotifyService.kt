package com.lucianghimpu.matchmefy.data.dataServices

import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User

interface SpotifyService {
    suspend fun getUserProfile() : User
    suspend fun getTopArtists() : List<Artist>
    suspend fun getTopTracks() : List<Track>
}