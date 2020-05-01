package com.lucianghimpu.matchmefy.data.networking.retrofitInterfaces

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.CompleteUserData
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchmefyApiService {

    @GET("/users/{id}")
    suspend fun getUserData(@Path("id") id: String) : CompleteUserData

    @GET("/search")
    fun getSearchUsers(@Query("q") searchQuery: String,
                       @Query("limit") limit: Int,
                       @Query("offset") offset: Int) : Call<SearchUsersResult>
}