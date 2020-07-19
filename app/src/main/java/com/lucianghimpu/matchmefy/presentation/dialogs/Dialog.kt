package com.lucianghimpu.matchmefy.presentation.dialogs

import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan

abstract class Dialog {
    abstract val title: String
    abstract val description: String
    abstract val descriptionSpan: ColoredTextSpan?
}