package com.lucianghimpu.matchmefy.utilities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ColoredTextSpan(
    val startIndex : Int = -1,
    val endIndex : Int = -1
) : Parcelable