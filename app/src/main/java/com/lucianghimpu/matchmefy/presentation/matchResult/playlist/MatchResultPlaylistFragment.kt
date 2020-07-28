package com.lucianghimpu.matchmefy.presentation.matchResult.playlist

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMathResultPlaylistBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MatchResultPlaylistFragment : BaseFragment<MatchResultViewModel, FragmentMathResultPlaylistBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel(from = { parentFragment!! })
    override fun getLayoutResId(): Int = R.layout.fragment_math_result_playlist
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}
