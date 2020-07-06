package com.lucianghimpu.matchmefy.presentation.settings

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentSettingsBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {
    override val viewModel: SettingsViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_settings
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}
