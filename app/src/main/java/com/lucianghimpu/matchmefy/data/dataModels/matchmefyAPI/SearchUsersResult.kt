package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

import com.lucianghimpu.matchmefy.data.dataModels.User

data class SearchUsersResult (
    val users: List<User>,
    val offset: Int,
    val total: Int,
    val limit: Int,
    val next: String,
    val prev: String
)