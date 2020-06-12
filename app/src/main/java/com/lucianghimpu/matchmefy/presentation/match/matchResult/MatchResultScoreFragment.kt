package com.lucianghimpu.matchmefy.presentation.match.matchResult

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultScoreBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MatchResultScoreFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultScoreBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_score
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}