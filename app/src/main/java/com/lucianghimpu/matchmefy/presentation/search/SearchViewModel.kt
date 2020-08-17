package com.lucianghimpu.matchmefy.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.ResourceProvider
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.microsoft.appcenter.analytics.Analytics
import kotlinx.coroutines.*

class SearchViewModel(
    private val matchmefyService: MatchmefyService,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private var lastJob: Job? = null
    private var lastSearchQuery = String.empty

    var searchText = MutableLiveData<String>()

    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun getSearchResults() {
        // avoid searching for the same query
        if (searchText.value != lastSearchQuery) {
            lastSearchQuery = searchText.value!!
            lastJob?.cancel("Cancelled last job caused by text change")
            lastJob = viewModelScope.launch {
                try {
                    isBusy.postValue(true)
                    // Debounce
                    delay(200)
                    val data = withContext(Dispatchers.IO) {
                        matchmefyService.getSearchUsers(searchText.value!!)
                    }
                    Log.i(LOG_TAG, "Fetched search results, count: ${data.users.size}")
                    _users.value = data.users
                }
                catch (ex: Exception) {
                    if (ex !is CancellationException) {
                        AppAnalytics.trackError(ex, "Error fetching search results: $ex")
                    }
                    _users.value = null
                }
                finally {
                    isBusy.postValue(false)
                }
            }
        }
    }

    fun onSearchResultClicked(user: User) {
        AppAnalytics.trackEvent("Search result clicked in ${this.javaClass.simpleName}")
        navigate(SearchFragmentDirections.actionSearchFragmentToUserPreviewFragment(user))
    }

    fun onFabClicked() {
        Analytics.trackEvent("FAB Clicked")
        throw Exception("Test ex")
//        showDialog(DoubleButtonDialog(
//            title = resourceProvider.getString(R.string.random_match_dialog_title),
//            imageId = R.drawable.dialog_random,
//            description = resourceProvider.getString(R.string.random_match_dialog_description),
//            descriptionSpan = ColoredTextSpan(13, 20),
//            positiveButtonText = resourceProvider.getString(R.string.match_dialog_button),
//            negativeButtonText = resourceProvider.getString(R.string.cancel_dialog_button),
//            listener = object : DoubleButtonDialogListener {
//                override fun onPositiveButtonClicked() {
//                    AppAnalytics.trackEvent("Match with random user clicked in ${this.javaClass.simpleName}")
//                    hideDialog()
//                    fetchRandomUser()
//                }
//
//                override fun onNegativeButtonClicked() {
//                    Log.i(LOG_TAG, "Cancel with random user")
//                    hideDialog()
//                }
//            }
//        ))
    }

    private fun fetchRandomUser() {
        viewModelScope.launch {
            try {
                isBusy.value = true
                showDialog(LoadingDialog())
                val randomUser = withContext(Dispatchers.IO) {
                    matchmefyService.getRandomUser()
                }
                Log.i(LOG_TAG, "Fetched random user")
                navigate(SearchFragmentDirections.actionSearchFragmentToUserPreviewFragment(randomUser))
            }
            catch (ex: Exception) {
                AppAnalytics.trackError(ex, "Error fetching random user: $ex")
            }
            finally {
                isBusy.value = false
                hideDialog()
            }
        }
    }
}