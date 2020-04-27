package com.lucianghimpu.matchmefy.presentation.search.pagination

import android.net.UrlQuerySanitizer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.services.MatchmefyService
import com.lucianghimpu.matchmefy.utilities.ApiConstants
import com.lucianghimpu.matchmefy.utilities.LogConstants
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NetworkState
import kotlinx.coroutines.*
import java.lang.Exception

class SearchResultsDataSource(
    private val matchmefyService: MatchmefyService,
    private val query: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<String, User>() {

    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, User>
    ) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val result = withContext(Dispatchers.IO) {
                matchmefyService.getSearchUsers(query, ApiConstants.DEFAULT_SEARCH_LIMIT, 0)
            }
            callback.onResult(result.users, null, result.next)
            networkState.postValue(NetworkState.SUCCESS)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, User>) {
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

        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val result = withContext(Dispatchers.IO) {
                matchmefyService.getSearchUsers(query, ApiConstants.DEFAULT_SEARCH_LIMIT, newOffset)
            }
            callback.onResult(result.users, result.next)
            networkState.postValue(NetworkState.SUCCESS)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, User>) {
        Log.i(LOG_TAG, "loadBefore override missing in SearchResultsDataSource.kt")
    }

    override fun invalidate() {
        super.invalidate()
        // Cancel possible running job to only keep last result searched by user
        supervisorJob.cancelChildren()
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(LOG_TAG, "An error occurred: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    fun getNetworkState(): LiveData<NetworkState> =
        networkState

    fun refresh() =
        this.invalidate()
}