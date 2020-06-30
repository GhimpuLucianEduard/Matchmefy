package com.lucianghimpu.matchmefy.presentation.matchResult

import com.lucianghimpu.matchmefy.utilities.Extensions.empty

data class CarouselItem(
    val title: String,
    val subtitle: String = String.empty,
    val imageUrl: String
)