package com.lucianghimpu.matchmefy.presentation.welcome

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentWelcomeProfileBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel


class WelcomeProfileFragment : BaseFragment<WelcomeViewModel, FragmentWelcomeProfileBinding>() {
    override val viewModel: WelcomeViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_welcome_profile
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}