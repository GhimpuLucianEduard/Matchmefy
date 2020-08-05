package com.lucianghimpu.matchmefy.presentation.matches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchesViewModel(
    private val matchmefyService: MatchmefyService,
    encryptedSharedPreferencesService: EncryptedSharedPreferencesService
): BaseViewModel() {

    private var user: User = encryptedSharedPreferencesService.getObject(
        PreferencesConstants.USER_PROFILE_KEY,
        User::class
    )!!

    var searchText = MutableLiveData<String>()

    private var _matches = MutableLiveData<List<MatchResult>>()
    val matches: LiveData<List<MatchResult>>
        get() = _matches

    init {
        if (!matchmefyService.initialMatchesLoaded()) {
            Log.i(LOG_TAG, "Loading initial matches")
            loadInitialMatches()
        } else {
            Log.i(LOG_TAG, "Inital matches loaded, fetching local matches")
            _matches.value = matchmefyService.getMatches(user.id, String.empty)
        }
    }

    fun filterMatches() {
        Log.i(LOG_TAG, "Filtering matches")
        _matches.value = matchmefyService.getMatches(user.id, searchText.value!!)
    }

    fun openMatchResult(matchResult: MatchResult) {
        Log.i(LOG_TAG, "Opening match result from matches tab")
        navigate(MatchesFragmentDirections.actionMatchFragmentToMatchResultFragment(matchResult))
    }

    private fun loadInitialMatches() {

        val loadMatchesDialog = LoadingDialog(
            "Loading"
        )

        viewModelScope.launch {
            try {
                showDialog(loadMatchesDialog)
                isBusy.value = true
                val matches = withContext(Dispatchers.IO) {
                    matchmefyService.loadInitialMatches(user.id)
                }
                _matches.value = matches
                Log.i(LOG_TAG, "Fetched ${matches.size} matches")
            } catch (ex: Exception) {
                Log.e(LOG_TAG, ex.toString())
            } finally {
                isBusy.value = false
                hideDialog()
            }
        }
    }
}