package com.lucianghimpu.matchmefy.data.networking.matchmefy

import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CreateUserRequest
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CreateUserResponse
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import retrofit2.http.*

interface MatchmefyApiService {

    @POST("/users")
    suspend fun postUserData(@Body createUserRequest: CreateUserRequest): CreateUserResponse

    @GET("/users/random")
    suspend fun getRandomUser(): User

    // N.B. api supports pagination but will not use it for now
    @GET("/search")
    suspend fun getSearchUsers(
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchUsersResult

    @POST("/match")
    suspend fun matchUsers(
        @Query("user") user: String
    ): MatchResult

    @GET("/match")
    suspend fun getMatches(): List<MatchResult>

    @DELETE("/users")
    suspend fun deleteUserData(): Any
}