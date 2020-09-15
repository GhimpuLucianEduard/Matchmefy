package com.lucianghimpu.matchmefy.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.appServices.Connectivity.ConnectivityService
import com.lucianghimpu.matchmefy.appServices.Connectivity.ConnectivityServiceListener
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.search.SearchFragmentDirections
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants.USER_PROFILE_KEY

/**
 * Assigned to the [MainActivity]
 * Used to handle android scenarios like OnActivityResult
 * being caught in the Activity but the handling should be done in a Fragment
 */
class MainActivityViewModel(
    application: Application,
    preferencesService: PreferencesService,
    connectivityService: ConnectivityService
) : BaseViewModel(application, preferencesService) {
    private var _connectivityChangedEvent = MutableLiveData<Event<Boolean>>()
    val connectivityChangedEvent: LiveData<Event<Boolean>>
        get() = _connectivityChangedEvent

    init {
        // get user profile from preferences OR redirect to login
        val user = preferencesService.getObject(USER_PROFILE_KEY, User::class)
        if (user == null) {
            navigate(SearchFragmentDirections.actionSearchFragmentToLoginFragment())
        }

        connectivityService.setConnectivityListener(object : ConnectivityServiceListener {
            override fun onConnected() {
                _connectivityChangedEvent.postValue(Event(true))
            }

            override fun onDisconnected() {
                _connectivityChangedEvent.postValue(Event(false))
            }
        })
    }
}