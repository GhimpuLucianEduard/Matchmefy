package com.lucianghimpu.matchmefy.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lucianghimpu.matchmefy.data.services.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.search.pagination.SearchResultsDataSourceFactory
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NetworkState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(
    private val matchmefyService: MatchmefyService
) : BaseViewModel() {

    private val searchResultsDataSource = SearchResultsDataSourceFactory(matchmefyService = matchmefyService, scope = viewModelScope)
    val users = LivePagedListBuilder(searchResultsDataSource, pagedListConfig()).build()
    val networkState : LiveData<NetworkState>? = switchMap(searchResultsDataSource.source) {
        it.getNetworkState()
    }

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(false)
        .setPageSize(5)
        .build()

    var searchText = MutableLiveData<String>()

    fun getSearchResults(query: String) {
        if (searchResultsDataSource.getQuery() == query) {
            return
        }
        searchResultsDataSource.updateQuery(query)
    }

    init {
        Log.d(LOG_TAG, "INIT SEARCH")
    }
}