package com.lucianghimpu.matchmefy.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.Auth.AppAuthService
import com.lucianghimpu.matchmefy.databinding.FragmentLoginBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.termsAndPrivacy.TermsAndPrivacyDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.termsAndPrivacy.TermsAndPrivacyDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.termsAndPrivacy.TermsAndPrivacyDialogListener
import com.lucianghimpu.matchmefy.utilities.DialogTagsConstants.TERMS_AND_PRIVACY_DIALOG_TAG
import com.lucianghimpu.matchmefy.utilities.WebConstants
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(
    exitTransitionResId = android.R.transition.fade
), TermsAndPrivacyDialogListener {

    override val viewModel: LoginViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_login
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private val appAuthService: AppAuthService by inject()

    private var termsAndPrivacyDialogFragment: TermsAndPrivacyDialogFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            AppAnalytics.trackEvent("login_button_clicked")
            termsAndPrivacyDialogFragment = TermsAndPrivacyDialogFragment(TermsAndPrivacyDialog(this))
            termsAndPrivacyDialogFragment!!.show(this.fragmentManager!!, TERMS_AND_PRIVACY_DIALOG_TAG)
        }
    }

    override fun onTermsClicked() {
        AppAnalytics.trackEvent("terms_login_clicked")
        openWebSite(WebConstants.MATCHMEFY_MOBILE_TERMS)
    }

    override fun onPrivacyPolicyClicked() {
        AppAnalytics.trackEvent("privacy_policy_login_clicked")
        openWebSite(WebConstants.MATCHMEFY_MOBILE_PRIVACY_POLICY)
    }

    override fun onPositiveButtonClicked() {
        AppAnalytics.trackEvent("terms_and_privacy_accept")
        appAuthService.sendAuthCodeRequest(activity!!)
        viewModel.onSignInClicked()
        termsAndPrivacyDialogFragment?.dismiss()
    }

    override fun onNegativeButtonClicked() {
        AppAnalytics.trackEvent("terms_and_privacy_cancel")
        termsAndPrivacyDialogFragment?.dismiss()
    }

    private fun openWebSite(url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
            AppAnalytics.trackLog("Opened URL $url in browser")
        } catch (ex: Exception) {
            AppAnalytics.trackError(ex, "Failed to open URL $url in browser")
        }
    }
}
