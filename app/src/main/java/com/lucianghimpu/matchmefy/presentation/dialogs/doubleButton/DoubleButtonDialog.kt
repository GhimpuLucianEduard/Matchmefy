package com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton

import android.os.Parcelable
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class DoubleButtonDialog(
    override val title: String,
    override val description: String,
    val imageId: Int,
    val positiveButtonText: String,
    val negativeButtonText: String,
    val listener: @RawValue DoubleButtonDialogListener
) : Dialog(), Parcelable