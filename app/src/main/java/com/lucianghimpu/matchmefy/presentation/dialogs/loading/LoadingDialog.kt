package com.lucianghimpu.matchmefy.presentation.dialogs.loading

import android.os.Parcelable
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoadingDialog(
    override val title: String,
    override val description: String,
    override val descriptionSpan: ColoredTextSpan? = null
) : Dialog(), Parcelable