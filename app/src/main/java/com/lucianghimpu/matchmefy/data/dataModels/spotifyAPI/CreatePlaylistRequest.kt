package com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI

data class CreatePlaylistRequest(
    val name: String,
    val description: String,
    val public: Boolean = false
)