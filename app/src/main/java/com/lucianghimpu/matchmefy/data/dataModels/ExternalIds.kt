package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExternalIds (
    val isrc : String
) : Parcelable