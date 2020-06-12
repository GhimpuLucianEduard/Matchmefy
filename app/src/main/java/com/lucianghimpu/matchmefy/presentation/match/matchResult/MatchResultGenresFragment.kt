package com.lucianghimpu.matchmefy.presentation.match.matchResult

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultGenresBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MatchResultGenresFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultGenresBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_genres
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}