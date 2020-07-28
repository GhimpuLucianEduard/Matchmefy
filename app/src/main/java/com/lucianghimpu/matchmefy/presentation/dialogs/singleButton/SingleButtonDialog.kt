package com.lucianghimpu.matchmefy.presentation.dialogs.singleButton

import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan

class SingleButtonDialog(
    val title: String,
    val description: String,
    val descriptionSpan: ColoredTextSpan? = null,
    val imageId: Int? = null,
    val buttonText: String,
    val listener: SingleButtonDialogListener? = null
) : Dialog()