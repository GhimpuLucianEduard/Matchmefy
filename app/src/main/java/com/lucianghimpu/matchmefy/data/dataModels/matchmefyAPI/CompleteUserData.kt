package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User

data class CompleteUserData (
    val user: User,
    val artists: List<Artist>,
    val tracks: List<Track>
)