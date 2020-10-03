package com.lucianghimpu.matchmefy.presentation.search

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.MatchmefyApp
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialogListener
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.extensions.empty
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class SearchViewModel(
    application: Application,
    preferencesService: PreferencesService,
    private val matchmefyService: MatchmefyService
) : BaseViewModel(application, preferencesService) {

    private val context: Context = this.getApplication<MatchmefyApp>().applicationContext

    private var lastJob: Job? = null
    private var lastSearchQuery = String.empty

    var searchText = MutableLiveData<String>()

    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    init {
        _isBusy.value = false
    }

    fun getSearchResults() {

        // clear for
        if (searchText.value == String.empty) {
            _users.value = emptyList()
            _isBusy.value = false
            return
        }

        // avoid searching for the same query
        if (searchText.value != lastSearchQuery && searchText.value!!.length >= 3) {
            lastSearchQuery = searchText.value!!
            lastJob?.cancel("Cancelled last job caused by text change")
            lastJob = viewModelScope.launch {
                try {
                    _isBusy.value = true
                    // Debounce
                    delay(200)
                    val data = withContext(Dispatchers.IO) {
                        matchmefyService.getSearchUsers(searchText.value!!)
                    }
                    Timber.d("Fetched search results, count: ${data.users.size}")
                    _users.value = data.users
                }
                catch (ex: Exception) {
                    when (ex) {
                        is CancellationException -> Timber.w("Cancelling search job")
                        is HttpException -> {
                            if (ex.code() == 400) {
                                Timber.w("Exception: $ex probably cused by cancellation")
                            } else {
                                handleError(ex)
                            }
                        }
                        else -> handleError(ex)
                    }
                    _users.value = null
                }
                finally {
                    _isBusy.value = false
                }
            }
        }
    }

    fun onSearchResultClicked(user: User) {
        AppAnalytics.trackEvent("search_result_clicked")
        navigate(SearchFragmentDirections.actionSearchFragmentToUserPreviewFragment(user))
    }

    fun onFabClicked() {
        showDialog(DoubleButtonDialog(
            title = context.getString(R.string.random_match_dialog_title),
            imageId = R.drawable.dialog_random,
            description = context.getString(R.string.random_match_dialog_description),
            descriptionSpan = ColoredTextSpan(13, 20),
            positiveButtonText = context.getString(R.string.match_dialog_button),
            negativeButtonText = context.getString(R.string.cancel_dialog_button),
            listener = object : DoubleButtonDialogListener {
                override fun onPositiveButtonClicked() {
                    AppAnalytics.trackEvent("match_random_user")
                    fetchRandomUser()
                }

                override fun onNegativeButtonClicked() {
                    AppAnalytics.trackEvent("cancel_match_random_user")
                }
            }
        ))
    }

    private fun fetchRandomUser() {
        viewModelScope.launch {
            try {
                _isBusy.value = true
                showDialog(LoadingDialog())
                val randomUser = withContext(Dispatchers.IO) {
                    matchmefyService.getRandomUser()
                }
                Timber.d("Fetched random user: ${randomUser.display_name}")
                AppAnalytics.trackLog("Fetched random user")
                hideLoadingDialog()
                navigate(SearchFragmentDirections.actionSearchFragmentToUserPreviewFragment(randomUser))
            }
            catch (ex: Exception) {
                hideLoadingDialog()
                handleError(ex)
            }
            finally {
                _isBusy.value = false
            }
        }
    }
}