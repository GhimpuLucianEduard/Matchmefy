package com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI

import com.lucianghimpu.matchmefy.data.dataModels.User

data class SearchUsersResult (
    val users: List<User> = listOf(User(), User(), User(), User(), User()),
    val offset: Int = 0,
    val total: Int = 100,
    val limit: Int = 5,
    val next: String = "2",
    val prev: String = "0"
)