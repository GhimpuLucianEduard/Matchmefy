package com.lucianghimpu.matchmefy.presentation.welcome

import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.extensions.empty

data class OnBoardingState(
    val title: String = String.empty,
    val description: String = String.empty,
    val imageId: Int = 0,
    val coloredTextSpan: ColoredTextSpan = ColoredTextSpan()
)