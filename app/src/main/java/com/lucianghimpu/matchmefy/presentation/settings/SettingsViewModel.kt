package com.lucianghimpu.matchmefy.presentation.settings

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.MatchmefyApp
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.data.dataServices.MatchmefyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialogListener
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SettingsViewModel(
    application: Application,
    preferencesService: PreferencesService,
    private val matchmefyService: MatchmefyService
) : BaseViewModel(application, preferencesService) {

    private val context: Context = this.getApplication<MatchmefyApp>().applicationContext

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        _user.value = preferencesService.getObject(
            PreferencesConstants.USER_PROFILE_KEY,
            User::class
        )
    }

    fun onDeleteDataClicked() {
        AppAnalytics.trackEvent("delete_data_clicked")
        val deleteUserDataDialog = DoubleButtonDialog(
            title = context.getString(R.string.delete_data_dialog_title),
            description = context.getString(R.string.delete_data_dialog_description),
            descriptionSpan = ColoredTextSpan(
                51,
                63
            ),
            positiveButtonText = context.getString(R.string.delete_data_dialog_button),
            negativeButtonText = context.getString(R.string.cancel_dialog_button),
            imageId = R.drawable.dialog_delete,
            listener = object : DoubleButtonDialogListener {
                override fun onPositiveButtonClicked() {
                    AppAnalytics.trackEvent("delete_data")
                    hideDialog()
                    deleteUserData()
                }

                override fun onNegativeButtonClicked() {
                    AppAnalytics.trackEvent("cancel_delete_data")
                    hideDialog()
                }

            }
        )
        showDialog(deleteUserDataDialog)
    }

    fun onSignOutClicked() {
        AppAnalytics.trackEvent("sign_out_clicked")
        val signOutDialog = DoubleButtonDialog(
            title = context.getString(R.string.sign_out_dialog_title),
            description = context.getString(R.string.sign_out_dialog_description),
            descriptionSpan = ColoredTextSpan(
                50,
                53
            ),
            positiveButtonText = context.getString(R.string.sign_out_dialog_button),
            negativeButtonText = context.getString(R.string.cancel_dialog_button),
            imageId = R.drawable.dialog_warning,
            listener = object : DoubleButtonDialogListener {
                override fun onPositiveButtonClicked() {
                    AppAnalytics.trackEvent("sign_out")
                    hideDialog()
                    signOut()
                }

                override fun onNegativeButtonClicked() {
                    AppAnalytics.trackEvent("cancel_sign_out")
                    hideDialog()
                }
            }
        )
        showDialog(signOutDialog)
    }

    private fun deleteUserData() {
        showDialog(LoadingDialog())
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    matchmefyService.deleteUserData(_user.value!!.id)
                }
                hideDialog()
                signOut()
                Timber.d("User data deleted")
            } catch (ex: Exception) {
                hideDialog()
                handleError(ex)
            }
        }
        // N.B. don't hide the loading dialog in a finally clause
    }

    private fun signOut() {
        matchmefyService.deleteAll()
        preferencesService.deleteAll()
        navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
    }
}
