package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

import android.os.Parcelable
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchResult(
    val artistsScore: Number = 0,
    val matchingArtists: List<Artist> = emptyList(),
    val tracksScore: Number = 0,
    val matchingTracks: List<Track> = emptyList(),
    val genresScore: Number = 0,
    val matchingGenres: List<String> = emptyList(),
    val matchingScore: Number = 0
) : Parcelable