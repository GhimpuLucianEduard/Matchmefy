package com.lucianghimpu.matchmefy.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.EncryptedSharedPreferencesService
import com.lucianghimpu.matchmefy.appServices.ResourceProvider
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialogListener
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants

class SettingsViewModel(
    private val encryptedSharedPreferencesService: EncryptedSharedPreferencesService,
    private val resourceProvider: ResourceProvider
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

    fun onSignOutClicked() {
        val signOutDialog = DoubleButtonDialog(
            title = resourceProvider.getString(R.string.sign_out_dialog_title),
            description = resourceProvider.getString(R.string.sign_out_dialog_description),
            positiveButtonText = resourceProvider.getString(R.string.sign_out_dialog_button),
            negativeButtonText = resourceProvider.getString(R.string.cancel_dialog_button),
            imageId = R.drawable.dialog_warning,
            descriptionSpan = ColoredTextSpan(
                50,
                53
            ),
            listener = object : DoubleButtonDialogListener {
                override fun onPositiveButtonClicked() {
                    hideDialog()
                    encryptedSharedPreferencesService.deleteAll()
                    navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
                    AppAnalytics.trackEvent("User signed out the app")
                }

                override fun onNegativeButtonClicked() {
                    hideDialog()
                    AppAnalytics.trackEvent("User cancelled sign out")
                }
            }
        )

        showDialog(signOutDialog)
    }
}
