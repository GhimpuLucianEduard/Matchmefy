package com.lucianghimpu.matchmefy.presentation.search.pagination

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.services.MatchmefyService
import java.util.concurrent.Executor

class SearchResultsRepository(
    private val matchmefyService: MatchmefyService,
    private val networkExecutor: Executor
) {

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()

    @MainThread
    fun getSearchResults(query: String): Listing<User> {
        val sourceFactory = SearchResultsDataSourceFactory(matchmefyService, query, networkExecutor)

        val livePagedList = LivePagedListBuilder(sourceFactory, pagedListConfig())
            .setFetchExecutor(networkExecutor)
            .build()

        val refreshState = switchMap(sourceFactory.source) {
            it.initialLoad
        }

        return Listing(
            pagedList = livePagedList,
            networkState = switchMap(sourceFactory.source) {
                it.networkState
            },
            retry = {
                sourceFactory.source.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.source.value?.invalidate()
            },
            refreshState = refreshState
        )
    }
}