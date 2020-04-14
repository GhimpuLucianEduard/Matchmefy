package com.lucianghimpu.matchmefy.data.dataModels

import com.lucianghimpu.matchmefy.utilities.Extensions.empty

data class User(
    val display_name : String = String.empty,
    val email : String = String.empty,
    val external_urls : ExternalUrls = ExternalUrls(),
    val followers : Followers = Followers(),
    val href : String = String.empty,
    val id : String = String.empty,
    val images : List<Image> = emptyList(),
    val type : String = String.empty,
    val uri : String = String.empty
)
