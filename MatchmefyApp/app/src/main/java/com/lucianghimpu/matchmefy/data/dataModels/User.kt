package com.lucianghimpu.matchmefy.data.dataModels

data class User(
    val display_name : String,
    val email : String,
    val href : String,
    val id : String,
    val images : List<UserImage>,
    val type : String,
    val uri : String
)
