package com.lucianghimpu.matchmefy.presentation.matchResult.score

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultScoreBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MatchResultScoreFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultScoreBinding>() {
    override val viewModel: MatchResultViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_score
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}