package com.lucianghimpu.matchmefy.data.networking.matchmefy

import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import retrofit2.http.*

interface MatchmefyApiService {

    @POST("/users")
    suspend fun postUserData(@Body completeUserData: CompleteUserData)

    @GET("/users/random")
    suspend fun getRandomUser(): User

    // N.B. Api supports pagination but will not use it for now
    @GET("/search")
    suspend fun getSearchUsers(
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchUsersResult

    @POST("/match")
    suspend fun matchUsers(
        @Query("firstUser") firstUser: String,
        @Query("secondUser") secondUser: String
    ): MatchResult

    @GET("/match")
    suspend fun getMatches(@Query("userId") userId: String): List<MatchResult>

    @DELETE("/users")
    suspend fun deleteUserData(@Query("userId") userId: String): Any
}