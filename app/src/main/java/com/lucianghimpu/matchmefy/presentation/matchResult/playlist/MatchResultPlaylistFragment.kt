package com.lucianghimpu.matchmefy.presentation.matchResult.playlist

import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultPlaylistBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MatchResultPlaylistFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultPlaylistBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel(from = { parentFragment!! })
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_playlist
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }
}
