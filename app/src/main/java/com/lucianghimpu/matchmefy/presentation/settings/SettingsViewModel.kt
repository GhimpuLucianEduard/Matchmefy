package com.lucianghimpu.matchmefy.presentation.settings

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.BuildConfig
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
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.PreferencesConstants
import com.lucianghimpu.matchmefy.utilities.WebConstants
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

    private var _appVersion = MutableLiveData<String>()
    val appVersion: LiveData<String>
        get() = _appVersion

    private var _openLinkInBrowserEvent = MutableLiveData<Event<String>>()
    val openLinkInBrowserEvent: MutableLiveData<Event<String>>
        get() = _openLinkInBrowserEvent

    private var _openLinkInBrowserAboutEvent = MutableLiveData<Event<String>>()
    val openLinkInBrowserAboutEvent: MutableLiveData<Event<String>>
        get() = _openLinkInBrowserAboutEvent

    private var _openEmailEvent = MutableLiveData<Event<Any>>()
    val openEmailEvent: MutableLiveData<Event<Any>>
        get() = _openEmailEvent

    init {
        _user.value = preferencesService.getObject(
            PreferencesConstants.USER_PROFILE_KEY,
            User::class
        )

        _appVersion.value = "AppVersion: ${BuildConfig.VERSION_NAME}"
    }

    fun openPrivacyPolicy() {
        AppAnalytics.trackEvent("privacy_policy_settings_clicked")
        _openLinkInBrowserEvent.value = Event(WebConstants.MATCHMEFY_MOBILE_PRIVACY_POLICY)
    }

    fun openTerms() {
        AppAnalytics.trackEvent("terms_settings_clicked")
        _openLinkInBrowserEvent.value = Event(WebConstants.MATCHMEFY_MOBILE_TERMS)
    }

    fun onAboutClicked() {
        AppAnalytics.trackEvent("about_clicked")
        navigate(SettingsFragmentDirections.actionSettingsFragmentToAboutFragment())
    }

    fun openGitHubPage() {
        AppAnalytics.trackEvent("github_clicked")
        _openLinkInBrowserAboutEvent.value = Event(WebConstants.MATCHMEFY_GITHUB)
    }

    fun openWebsite() {
        AppAnalytics.trackEvent("website_clicked")
        _openLinkInBrowserAboutEvent.value = Event(WebConstants.MATCHMEFY_WEB)
    }

    fun openEmail() {
        AppAnalytics.trackEvent("email_clicked")
        _openEmailEvent.value = Event(Any())
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
                    signOut()
                }

                override fun onNegativeButtonClicked() {
                    AppAnalytics.trackEvent("cancel_sign_out")
                }
            }
        )
        showDialog(signOutDialog)
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
                    deleteUserData()
                }

                override fun onNegativeButtonClicked() {
                    AppAnalytics.trackEvent("cancel_delete_data")
                }

            }
        )
        showDialog(deleteUserDataDialog)
    }

    private fun deleteUserData() {
        showDialog(LoadingDialog())
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    matchmefyService.deleteUserData(_user.value!!.id)
                }
                hideLoadingDialog()
                signOut()
                Timber.d("User data deleted")
            } catch (ex: Exception) {
                hideLoadingDialog()
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
