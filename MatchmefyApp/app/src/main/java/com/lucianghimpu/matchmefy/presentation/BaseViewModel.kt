package com.lucianghimpu.matchmefy.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.lucianghimpu.matchmefy.utilities.Event

abstract class BaseViewModel : ViewModel() {

    var navigationEvent = MutableLiveData<Event<NavDirections>>()
    var isBusy = MutableLiveData<Boolean>()

    fun navigate(directions: NavDirections) {
        navigationEvent.value = Event(directions)
    }
}