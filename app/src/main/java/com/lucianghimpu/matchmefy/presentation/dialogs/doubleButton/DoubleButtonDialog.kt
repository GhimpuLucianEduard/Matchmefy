package com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton

import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan

class DoubleButtonDialog(
    val title: String,
    val description: String,
    val descriptionSpan: ColoredTextSpan? = null,
    val imageId: Int? = null,
    val positiveButtonText: String,
    val negativeButtonText: String,
    val listener: DoubleButtonDialogListener? = null
) : Dialog()