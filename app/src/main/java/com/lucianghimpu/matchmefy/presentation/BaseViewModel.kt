package com.lucianghimpu.matchmefy.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.lucianghimpu.matchmefy.MatchmefyApp
import com.lucianghimpu.matchmefy.NavigationGraphDirections
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.presentation.dialogs.Dialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialogListener
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.NavigationCommand
import retrofit2.HttpException

abstract class BaseViewModel(
    application: Application,
    protected val preferencesService: PreferencesService
) : AndroidViewModel(application) {

    private var _showDialogEvent = MutableLiveData<Event<Dialog>>()
    val showDialogEvent: LiveData<Event<Dialog>>
        get() = _showDialogEvent

    private var _navigationEvent = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>>
        get() = _navigationEvent

    protected var _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private var _hideLoadingDialogEvent = MutableLiveData<Event<Any>>()
    val hideLoadingDialogEvent: LiveData<Event<Any>>
        get() = _hideLoadingDialogEvent

    fun navigate(directions: NavDirections) {
        _navigationEvent.value = Event(NavigationCommand.To(directions))
    }

    fun navigateBack() {
        _navigationEvent.value = Event(NavigationCommand.Back)
    }

    fun showDialog(dialog: Dialog) {
        _showDialogEvent.value = Event(dialog)
    }

    /**
     * Hides last visible loading dialog
     */
    fun hideLoadingDialog() {
        _hideLoadingDialogEvent.value = Event(Any())
    }

    fun handleError(ex: Exception) {
        AppAnalytics.trackError(ex)
        val context = this.getApplication<MatchmefyApp>().applicationContext
        if (ex is HttpException && ex.code() == 401) {
            showDialog(SingleButtonDialog(
                title =  context.getString(R.string.unauthorized_dialog_title),
                description = context.getString(R.string.unauthorized_dialog_description),
                descriptionSpan = ColoredTextSpan(62, 67),
                imageId = R.drawable.dialog_warning,
                buttonText = context.getString(R.string.error_dialog_button),
                listener = object : SingleButtonDialogListener {
                    override fun onButtonClicked() {
                        AppAnalytics.trackEvent("session_expired_redirect_to_login_clicked")
                        preferencesService.deleteAll()
                        navigate(NavigationGraphDirections.actionGlobalLoginFragment())
                    }
                }
            ))
        } else {
            showDialog(SingleButtonDialog(
                title =  context.getString(R.string.error_dialog_title),
                description = context.getString(R.string.error_dialog_description),
                descriptionSpan = ColoredTextSpan(27, 36),
                imageId = R.drawable.dialog_warning,
                buttonText = context.getString(R.string.error_dialog_button),
                listener = object : SingleButtonDialogListener {
                    override fun onButtonClicked() {
                        AppAnalytics.trackEvent("generic_error_ok_clicked")
                    }
                }
            ))
        }
    }
}