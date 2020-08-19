package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

import android.os.Parcelable
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.utilities.extensions.empty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchResult(
    val _id: String = String.empty,
    val artistsScore: Number = 0,
    val matchingArtists: List<Artist> = emptyList(),
    val tracksScore: Number = 0,
    val matchingTracks: List<Track> = emptyList(),
    val genresScore: Number = 0,
    val matchingGenres: List<String> = emptyList(),
    val matchingScore: Number = 0,
    val playlistForSpotify: PlaylistForSpotify,
    val user: User,
    val matchingUser: User
) : Parcelable