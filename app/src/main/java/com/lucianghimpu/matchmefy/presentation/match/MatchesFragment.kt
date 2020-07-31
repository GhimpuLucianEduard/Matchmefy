package com.lucianghimpu.matchmefy.presentation.match

import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchesBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MatchesFragment : BaseFragment<MatchesViewModel, FragmentMatchesBinding>() {

    override val viewModel: MatchesViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_matches
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavigationBarVisibility(View.VISIBLE)
    }
}
