package com.lucianghimpu.matchmefy.presentation.welcome

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentWelcomeBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeViewModel, FragmentWelcomeBinding>() {

    override val viewModel: WelcomeViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_welcome
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}
