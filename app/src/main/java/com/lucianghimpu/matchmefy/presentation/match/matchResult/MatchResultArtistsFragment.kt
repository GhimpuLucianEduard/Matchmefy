package com.lucianghimpu.matchmefy.presentation.match.matchResult

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultArtistsBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MatchResultArtistsFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultArtistsBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_artists
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}