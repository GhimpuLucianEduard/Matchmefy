package com.lucianghimpu.matchmefy.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.databinding.FragmentSettingsBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.utilities.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>(
    enterTransitionResId = android.R.transition.fade,
    exitTransitionResId = android.R.transition.fade
) {
    override val viewModel: SettingsViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_settings
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavigationBarVisibility(View.VISIBLE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openLinkInBrowserEvent.observe(this@SettingsFragment, EventObserver {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
                AppAnalytics.trackLog("Opened URL $it in browser")
            } catch (ex: Exception) {
                AppAnalytics.trackError(ex, "Failed to open URL $it in browser")
            }
        })
    }
}
