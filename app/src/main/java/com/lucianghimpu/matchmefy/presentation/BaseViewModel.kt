package com.lucianghimpu.matchmefy.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.lucianghimpu.matchmefy.MatchmefyApp
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialogListener
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.NavigationCommand

abstract class BaseViewModel(
    application: Application
) : AndroidViewModel(application) {

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
        showDialogEvent.postValue(Event(dialog))
    }

    /**
     * Hides last visible dialog
     */
    fun hideDialog() {
        _hideDialogEvent.postValue(Event(Any()))
    }

    fun handleError(ex: Exception) {
        AppAnalytics.trackError(ex)
        val context = this.getApplication<MatchmefyApp>().applicationContext
        showDialog(SingleButtonDialog(
            title =  context.getString(R.string.error_dialog_title),
            description = context.getString(R.string.error_dialog_description),
            descriptionSpan = ColoredTextSpan(27, 36),
            imageId = R.drawable.dialog_warning,
            buttonText = context.getString(R.string.error_dialog_button),
            listener = object : SingleButtonDialogListener {
                override fun onButtonClicked() {
                    hideDialog()
                }
            }
        ))
    }
}