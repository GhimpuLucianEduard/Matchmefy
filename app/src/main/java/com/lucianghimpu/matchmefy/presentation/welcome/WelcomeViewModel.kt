package com.lucianghimpu.matchmefy.presentation.welcome

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.MatchmefyApp
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants

class WelcomeViewModel(
    application: Application,
    preferencesService: PreferencesService
) : BaseViewModel(application) {

    companion object {
        const val ON_BOARDING_STATES_COUNT: Int = 4
    }

    private val context: Context = this.getApplication<MatchmefyApp>().applicationContext

    private var states = ArrayList<OnBoardingState>()

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    init {
        _user.value = preferencesService.getObject(
            PreferencesConstants.USER_PROFILE_KEY,
            User::class
        )
        initStates()
    }

    fun onGetStartedClicked() {
        AppAnalytics.trackEvent("get_started_clicked")
        navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSearchFragment())
    }

    fun getStateForIndex(index: Int): OnBoardingState {
        return states[index]
    }

    private fun initStates() {
        states.addAll(
            listOf(
                OnBoardingState(), // for welcome, no need for an actual state
                OnBoardingState(
                    context.getString(R.string.on_boarding_title_1),
                    context.getString(R.string.on_boarding_description_1),
                    R.drawable.welcome_pic1,
                    ColoredTextSpan(0, 17)
                ),
                OnBoardingState(
                    context.getString(R.string.on_boarding_title_2),
                    context.getString(R.string.on_boarding_description_2),
                    R.drawable.welcome_pic2,
                    ColoredTextSpan(11, 23)
                ),
                OnBoardingState(
                    context.getString(R.string.on_boarding_title_3),
                    context.getString(R.string.on_boarding_description_3),
                    R.drawable.welcome_pic3,
                    ColoredTextSpan(56, 65)
                )
            )
        )
    }
}
