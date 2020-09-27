package com.lucianghimpu.matchmefy.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.databinding.FragmentAboutBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.utilities.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel


class AboutFragment : BaseFragment<SettingsViewModel, FragmentAboutBinding>(
    enterTransitionResId = android.R.transition.slide_right,
    exitTransitionResId = android.R.transition.fade
) {
    override val viewModel: SettingsViewModel by sharedViewModel(from = { parentFragment!! })
    override fun getLayoutResId(): Int = R.layout.fragment_about
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavigationBarVisibility(View.VISIBLE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.openLinkInBrowserAboutEvent.observe(this@AboutFragment, EventObserver {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
                AppAnalytics.trackLog("Opened URL $it in browser")
            } catch (ex: Exception) {
                AppAnalytics.trackError(ex, "Failed to open URL $it in browser")
            }
        })

        viewModel.openEmailEvent.observe(this@AboutFragment, EventObserver {
            try {
                val emailIntent = Intent(Intent.ACTION_SEND)

                emailIntent.type = "plain/text"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, listOf("matchmefy@gmail.com").toTypedArray())

                startActivity(Intent.createChooser(emailIntent, "Contact us via Email"))
            } catch (ex: Exception) {
                AppAnalytics.trackError(ex, "Failed to open email")
            }
        })
    }
}