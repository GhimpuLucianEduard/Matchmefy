package com.lucianghimpu.matchmefy.data.dataModels

import android.os.Parcelable
import com.lucianghimpu.matchmefy.utilities.extensions.empty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExternalUrls (
    val spotify : String = String.empty
) : Parcelable