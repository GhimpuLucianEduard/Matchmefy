package com.lucianghimpu.matchmefy.presentation.search.pagination

import android.net.UrlQuerySanitizer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.SearchUsersResult
import com.lucianghimpu.matchmefy.data.services.MatchmefyService
import com.lucianghimpu.matchmefy.utilities.ApiConstants
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class SearchResultsDataSource(
    private val matchmefyService: MatchmefyService,
    private val query: String,
    private val retryExecutor: Executor
) : PageKeyedDataSource<String, User>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, User>) {
        val request = matchmefyService.getSearchUsers(query, ApiConstants.DEFAULT_SEARCH_LIMIT, 0)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val data = response.body()
            val items = data?.users ?: emptyList()

            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)

            callback.onResult(items, data?.prev, data?.next)
        } catch (ex: Exception) {
            Log.e(LOG_TAG, ex.toString())
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ex.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, User>) {
        networkState.postValue(NetworkState.LOADING)

        val sanitizer = UrlQuerySanitizer()
        sanitizer.setAllowUnregisteredParamaters(true)
        sanitizer.parseUrl(params.key)
        Log.i(LOG_TAG, "Next Page: ${params.key}")
        var offset : String? = sanitizer.getValue("offset")

        offset?.let {
            if (it.contains("+")) {
                offset = it.replace("+", " ")
            }
        }

        val newOffset = offset!!.toInt()
        Log.i(LOG_TAG, "New Offset: ${newOffset}")

        val request = matchmefyService.getSearchUsers(query, ApiConstants.DEFAULT_SEARCH_LIMIT, newOffset)
            .enqueue(
            object : retrofit2.Callback<SearchUsersResult> {
                override fun onFailure(call: Call<SearchUsersResult>, t: Throwable) {
                    Log.e(LOG_TAG, t.toString())
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                }

                override fun onResponse(
                    call: Call<SearchUsersResult>,
                    response: Response<SearchUsersResult>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val items = data?.users ?: emptyList()
                        retry = null
                        callback.onResult(items, data?.next)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(
                            NetworkState.error("error code: ${response.code()}"))
                    }
                }
            }
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, User>) {
        Log.i(LOG_TAG, "loadBefore override missing in SearchResultsDataSource.kt")
    }
}