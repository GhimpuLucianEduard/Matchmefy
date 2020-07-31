package com.lucianghimpu.matchmefy.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.appServices.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserPreviewViewModel(
    private val matchmefyService: MatchmefyService,
    private val encryptedSharedPreferencesService: EncryptedSharedPreferencesService
) : BaseViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _matchingUser = MutableLiveData<User>()
    val matchingUser: LiveData<User>
        get() = _matchingUser

    fun initData(matchingUser: User) {
        _user.value = encryptedSharedPreferencesService.getObject(
            PreferencesConstants.USER_PROFILE_KEY,
            User::class
        )
        _matchingUser.value = matchingUser
    }

    fun onMatchClicked() {
        viewModelScope.launch {
            try {
                isBusy.value = true
                val data = withContext(Dispatchers.IO) {
                    matchmefyService.matchUsers(user.value!!.id, matchingUser.value!!.id)
                }
                Log.i(LOG_TAG, "Matched users with final score: ${data.matchingScore}")
                navigate(UserPreviewFragmentDirections.actionUserPreviewFragmentToMatchResultFragment(data))
            }
            catch (ex: Exception) {
                Log.e(LOG_TAG, "Error matching users: $ex")

            }
            finally {
                isBusy.value = false
            }
        }
    }
}
