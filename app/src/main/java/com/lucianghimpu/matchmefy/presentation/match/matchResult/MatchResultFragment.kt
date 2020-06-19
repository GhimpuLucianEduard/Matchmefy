package com.lucianghimpu.matchmefy.presentation.match.matchResult

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_match_result.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MatchResultFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultBinding>() {
    private val args: MatchResultFragmentArgs by navArgs()
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    lateinit var pageViewAdapter: MatchResultPageViewerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.matchResult.postValue(args.matchResult)

        pageViewAdapter = MatchResultPageViewerAdapter(this)
        viewPager.adapter = pageViewAdapter
        viewPager.isUserInputEnabled = false
        dotsIndicator.dotsClickable = false
        dotsIndicator.isClickable
        dotsIndicator.setViewPager2(viewPager)

        viewModel.state.observe(this@MatchResultFragment, Observer {
            viewPager.currentItem = it.ordinal
        })
    }

    /**
     * Returns the amount of pages that will be displayed based on the common data of both users.
     * The default should be 4 (score -> artists -> tracks -> genres)
     * @return a integer, the number of pages to be displayed in the UI
     */
    fun getCarouselPageCount(): Int = MatchResultViewModel.MatchResultState.values().size

}
