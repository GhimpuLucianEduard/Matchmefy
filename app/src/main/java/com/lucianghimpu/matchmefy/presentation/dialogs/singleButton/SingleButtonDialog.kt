package com.lucianghimpu.matchmefy.presentation.dialogs.singleButton

import android.os.Parcelable
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class SingleButtonDialog(
    override val title: String,
    override val description: String,
    val imageId: Int,
    val buttonText: String,
    val listener: @RawValue SingleButtonDialogListener
) : Dialog(), Parcelable