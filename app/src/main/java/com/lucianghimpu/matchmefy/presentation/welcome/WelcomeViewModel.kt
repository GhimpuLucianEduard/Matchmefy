package com.lucianghimpu.matchmefy.presentation.welcome

import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel

class WelcomeViewModel : BaseViewModel() {
    var userProfile = MutableLiveData<User>()

    fun onContinueClicked() {
        navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSearchFragment())
    }


}
