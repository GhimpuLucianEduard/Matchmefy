package com.lucianghimpu.matchmefy.presentation.dialogs.loading

import android.os.Parcelable
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoadingDialog(
    val title: String = "Loading"
) : Dialog(), Parcelable