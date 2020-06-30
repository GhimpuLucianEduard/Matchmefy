package com.lucianghimpu.matchmefy.presentation.matchResult.genres

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultGenresBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import kotlinx.android.synthetic.main.fragment_match_result_genres.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MatchResultGenresFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultGenresBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_genres
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private lateinit var flexBoxLayoutManager: FlexboxLayoutManager
    private lateinit var genresAdapter: GenresAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flexBoxLayoutManager =  FlexboxLayoutManager(activity).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.FLEX_START
        }

        genresAdapter = GenresAdapter()

        viewModel.matchResult.observe(viewLifecycleOwner, Observer {
            genresAdapter.submitList(it.matchingGenres)
        })

        genresRecyclerView.apply {
            layoutManager = flexBoxLayoutManager
            adapter = genresAdapter
        }
    }
}