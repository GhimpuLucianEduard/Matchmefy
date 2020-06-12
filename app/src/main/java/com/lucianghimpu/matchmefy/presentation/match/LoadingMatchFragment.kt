package com.lucianghimpu.matchmefy.presentation.match

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentLoadingMatchBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.SharedViewModel
import com.lucianghimpu.matchmefy.presentation.search.UserPreviewFragmentArgs
import org.koin.android.viewmodel.ext.android.viewModel

class LoadingMatchFragment : BaseFragment<LoadingMatchViewModel, FragmentLoadingMatchBinding>() {

    private val args: UserPreviewFragmentArgs by navArgs()
    override val viewModel: LoadingMatchViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_loading_match
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userProfile.value = args.user
        viewModel.userArtist = sharedViewModel.userArtists
        viewModel.userTracks = sharedViewModel.userTracks
    }
}
