package com.lucianghimpu.matchmefy.presentation.dialogs.singleButton

import android.os.Parcelable
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class SingleButtonDialog(
    override val title: String,
    override val description: String,
    override val descriptionSpan: ColoredTextSpan? = null,
    val imageId: Int? = null,
    val buttonText: String,
    val listener: @RawValue SingleButtonDialogListener? = null
) : Dialog(), Parcelable