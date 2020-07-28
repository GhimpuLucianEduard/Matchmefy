package com.lucianghimpu.matchmefy.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    // TODO: make them private and use LiveData
    var showDialogEvent = MutableLiveData<Event<Dialog>>()
    var navigationEvent = MutableLiveData<Event<NavigationCommand>>()
    var isBusy = MutableLiveData<Boolean>()

    private var _hideDialogEvent = MutableLiveData<Event<Any>>()
    val hideDialogEvent: MutableLiveData<Event<Any>>
        get() = _hideDialogEvent

    fun navigate(directions: NavDirections) {
        navigationEvent.value = Event(NavigationCommand.To(directions))
    }

    fun navigateBack() {
        navigationEvent.value = Event(NavigationCommand.Back)
    }

    fun showDialog(dialog: Dialog) {
        showDialogEvent.value = Event(dialog)
    }

    /**
     * Hides last visible dialog
     */
    fun hideDialog() {
        _hideDialogEvent.value = Event(Any())
    }
}