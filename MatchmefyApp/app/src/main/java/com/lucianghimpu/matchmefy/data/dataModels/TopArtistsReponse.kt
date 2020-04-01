package com.lucianghimpu.matchmefy.data.dataModels

data class TopArtistsReponse(
    val items : List<Artist>,
    val total : Int,
    val limit : Int,
    val offset : Int,
    val previous : String,
    val href : String,
    val next : String
)