package com.lucianghimpu.matchmefy.presentation.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserPreviewViewModel(
    private val matchmefyService: MatchmefyService
) : BaseViewModel() {

    var matchingUser =  MutableLiveData<User>()
    lateinit var currentUser: User

    fun onMatchClicked() {
        viewModelScope.launch {
            try {
                isBusy.postValue(true)
                val data = withContext(Dispatchers.IO) {
                    matchmefyService.matchUsers(currentUser.id, matchingUser.value!!.id)
                }
                Log.i(LOG_TAG, "Matched users with final score: ${data.matchingScore}")
                navigate(UserPreviewFragmentDirections.actionUserPreviewFragmentToMatchResultFragment(data))
            }
            catch (ex: Exception) {
                Log.e(LOG_TAG, "Error matching users: $ex")

            }
            finally {
                isBusy.postValue(false)
            }
        }
    }
}
