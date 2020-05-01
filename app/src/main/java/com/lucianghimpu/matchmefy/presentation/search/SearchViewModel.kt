package com.lucianghimpu.matchmefy.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lucianghimpu.matchmefy.data.services.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.search.pagination.SearchResultsDataSourceFactory
import com.lucianghimpu.matchmefy.presentation.search.pagination.SearchResultsRepository
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SearchViewModel(
    private val matchmefyService: MatchmefyService
) : BaseViewModel() {

    private val NETWORK_IO = Executors.newFixedThreadPool(5)
    fun getNetworkExecutor(): Executor = NETWORK_IO

    private val repository = SearchResultsRepository(matchmefyService, getNetworkExecutor())
    var searchText = MutableLiveData<String>()

    val users = switchMap(searchText) {
        repository.getSearchResults(it).pagedList
    }

//    val networkState = repoResult.switchMap { it.networkState }
//    val refreshState = repoResult.switchMap { it.refreshState }

    fun getSearchResults(query: String) {
        searchText.value = query;
    }

    init {
        Log.d(LOG_TAG, "INIT SEARCH")
    }
}