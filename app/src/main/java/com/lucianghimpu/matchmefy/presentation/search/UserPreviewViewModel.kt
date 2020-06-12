package com.lucianghimpu.matchmefy.presentation.search

import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel

class UserPreviewViewModel : BaseViewModel() {
    var userProfile =  MutableLiveData<User>()

    fun onMatchClicked() {
        navigate(UserPreviewFragmentDirections.actionUserPreviewFragmentToLoadingMatchFragment(userProfile.value!!))
    }
}
