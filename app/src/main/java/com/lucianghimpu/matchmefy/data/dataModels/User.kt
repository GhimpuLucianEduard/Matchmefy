package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Parcelable
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val display_name : String = String.empty,
    val email : String = String.empty,
    val external_urls : ExternalUrls = ExternalUrls(),
    val id : String = String.empty,
    val images : List<Image> = emptyList(),
    val uri : String = String.empty
) : Parcelable