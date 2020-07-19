package com.lucianghimpu.matchmefy.utilities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ColoredTextSpan(
    val startIndex : Int,
    val endIndex : Int
) : Parcelable