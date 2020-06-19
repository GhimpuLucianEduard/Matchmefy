package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val external_urls : ExternalUrls,
    val followers : Followers,
    val genres : List<String>,
    val href : String,
    val id : String,
    val images : List<Image>,
    val name : String,
    val popularity : Int,
    val type : String,
    val uri : String
) : Parcelable