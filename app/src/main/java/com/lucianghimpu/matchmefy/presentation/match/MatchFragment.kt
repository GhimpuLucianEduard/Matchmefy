package com.lucianghimpu.matchmefy.presentation.match

import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MatchFragment : BaseFragment<MatchViewModel, FragmentMatchBinding>() {

    override val viewModel: MatchViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavigationBarVisibility(View.VISIBLE)
    }
}
