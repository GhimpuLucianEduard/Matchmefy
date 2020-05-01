package com.lucianghimpu.matchmefy.presentation.search.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.services.MatchmefyService
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import java.util.concurrent.Executor

class SearchResultsDataSourceFactory(
    private val matchmefyService: MatchmefyService,
    private var query: String = String.empty,
    private val retryExecutor: Executor
): DataSource.Factory<String, User>() {
    val source = MutableLiveData<SearchResultsDataSource>()
    override fun create(): DataSource<String, User> {
        val source = SearchResultsDataSource(matchmefyService, query, retryExecutor)
        this.source.postValue(source)
        return source
    }
}