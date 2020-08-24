package com.lucianghimpu.matchmefy.presentation.welcome

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants

class WelcomeViewModel(
    application: Application,
    preferencesService: PreferencesService
) : BaseViewModel(application) {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        _user.value = preferencesService.getObject(
            PreferencesConstants.USER_PROFILE_KEY,
            User::class
        )
    }

    fun onContinueClicked() {
        navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSearchFragment())
        AppAnalytics.trackEvent("welcome_continue_clicked")
    }
}
