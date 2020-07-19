package com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton

import android.os.Parcelable
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class DoubleButtonDialog(
    override val title: String,
    override val description: String,
    override val descriptionSpan: ColoredTextSpan? = null,
    val imageId: Int? = null,
    val positiveButtonText: String,
    val negativeButtonText: String,
    val listener: @RawValue DoubleButtonDialogListener? = null
) : Dialog(), Parcelable