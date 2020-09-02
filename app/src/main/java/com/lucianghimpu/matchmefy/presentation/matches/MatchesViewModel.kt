package com.lucianghimpu.matchmefy.presentation.matches

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import com.lucianghimpu.matchmefy.utilities.extensions.empty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MatchesViewModel(
    application: Application,
    private val matchmefyService: MatchmefyService,
    preferencesService: PreferencesService
): BaseViewModel(application) {

    private var user: User = preferencesService.getObject(
        PreferencesConstants.USER_PROFILE_KEY,
        User::class
    )!!

    var searchText = MutableLiveData<String>()

    private var _matches = MutableLiveData<List<MatchResult>>()
    val matches: LiveData<List<MatchResult>>
        get() = _matches

    init {
        isBusy.value = false
        if (!matchmefyService.initialMatchesLoaded()) {
            Timber.d("Loading initial matches")
            loadInitialMatches()
        } else {
            Timber.d("Initial matches loaded, fetching local matches")
            _matches.value = matchmefyService.getMatches(user.id, String.empty)
        }
    }

    fun filterMatches() {
        if (!isBusy.value!!) {
            Timber.d("Filtering matches, filter: ${searchText.value!!}")
            _matches.value = matchmefyService.getMatches(user.id, searchText.value!!)
        }
    }

    fun openMatchResult(matchResult: MatchResult) {
        navigate(MatchesFragmentDirections.actionMatchFragmentToMatchResultFragment(matchResult))
    }

    private fun loadInitialMatches() {
        viewModelScope.launch {
            try {
                isBusy.value = true
                val matches = withContext(Dispatchers.IO) {
                    matchmefyService.loadInitialMatches(user.id)
                }
                _matches.value = matches
                AppAnalytics.trackLog("Fetched matches")
            } catch (ex: Exception) {
                handleError(ex)
            } finally {
                isBusy.value = false
            }
        }
    }
}
