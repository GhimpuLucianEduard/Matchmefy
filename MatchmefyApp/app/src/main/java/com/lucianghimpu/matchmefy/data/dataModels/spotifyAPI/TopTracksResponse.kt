package com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI

import com.lucianghimpu.matchmefy.data.dataModels.Track

data class TopTracksResponse (
    val items : List<Track>,
    val total : Int,
    val limit : Int,
    val offset : Int,
    val previous : String,
    val href : String,
    val next : String
)