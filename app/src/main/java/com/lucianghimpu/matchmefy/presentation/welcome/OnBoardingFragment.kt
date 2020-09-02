package com.lucianghimpu.matchmefy.presentation.welcome

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentOnBoardingBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class OnBoardingFragment(
    private val state: OnBoardingState
) : BaseFragment<WelcomeViewModel, FragmentOnBoardingBinding>() {
    override val viewModel: WelcomeViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_on_boarding
    override fun setViewDataBindingViewModel() { binding.onBoardingState = state }

}