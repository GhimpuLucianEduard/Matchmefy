package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

import android.os.Parcelable
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaylistForSpotify(
    val name: String = String.empty,
    val description: String = String.empty,
    val uris: List<String> = emptyList()
) : Parcelable