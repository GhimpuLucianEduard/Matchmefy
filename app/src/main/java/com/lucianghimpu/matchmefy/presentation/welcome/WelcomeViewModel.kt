package com.lucianghimpu.matchmefy.presentation.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants

class WelcomeViewModel(
    encryptedSharedPreferencesService: EncryptedSharedPreferencesService
) : BaseViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        _user.value = encryptedSharedPreferencesService.getObject(
            PreferencesConstants.USER_PROFILE_KEY,
            User::class
        )
    }

    fun onContinueClicked() {
        navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSearchFragment())
        AppAnalytics.trackEvent("Continue button clicked in ${this.javaClass.simpleName}")
    }
}
