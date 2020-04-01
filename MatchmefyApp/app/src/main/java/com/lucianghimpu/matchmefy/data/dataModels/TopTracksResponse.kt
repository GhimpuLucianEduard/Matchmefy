package com.lucianghimpu.matchmefy.data.dataModels

data class TopTracksResponse (
    val items : List<Track>,
    val total : Int,
    val limit : Int,
    val offset : Int,
    val previous : String,
    val href : String,
    val next : String
)