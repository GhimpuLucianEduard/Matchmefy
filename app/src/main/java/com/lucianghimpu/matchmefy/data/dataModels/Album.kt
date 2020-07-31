package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album (
    val artists : List<Artist>,
    val external_urls : ExternalUrls,
    val id : String,
    val images : List<Image>,
    val name : String,
    val uri : String
) : Parcelable