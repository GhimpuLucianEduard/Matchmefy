package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track (
    val album : Album,
    val artists : List<Artist>,
    val external_urls : ExternalUrls,
    val id : String,
    val name : String,
    val preview_url : String,
    val uri : String
) : Parcelable