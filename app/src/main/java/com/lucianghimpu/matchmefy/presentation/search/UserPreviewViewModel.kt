package com.lucianghimpu.matchmefy.presentation.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserPreviewViewModel(
    application: Application,
    preferencesService: PreferencesService,
    private val matchmefyService: MatchmefyService
) : BaseViewModel(application, preferencesService) {
    private var _matchingUser = MutableLiveData<User>()
    val matchingUser: LiveData<User>
        get() = _matchingUser

    fun initData(matchingUser: User) {
        _matchingUser.value = matchingUser
    }

    fun onMatchClicked() {
        viewModelScope.launch {
            try {
                isBusy.value = true
                val data = withContext(Dispatchers.IO) {
                    matchmefyService.matchUsers(matchingUser.value!!.id)
                }
                Timber.d("Matched users, final score: ${data.matchingScore}")
                navigate(UserPreviewFragmentDirections.actionUserPreviewFragmentToMatchResultFragment(data))
            }
            catch (ex: Exception) {
                handleError(ex)
            }
            finally {
                isBusy.value = false
            }
        }
    }
}
