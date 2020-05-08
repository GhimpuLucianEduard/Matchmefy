package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Parcelable
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Followers (
    val href : String = String.empty,
    val total : Int = 90
) : Parcelable