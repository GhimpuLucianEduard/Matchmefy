package com.lucianghimpu.matchmefy.presentation.matches

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchesBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.utilities.extensions.addScrollToTopListener
import kotlinx.android.synthetic.main.fragment_matches.*
import org.koin.android.viewmodel.ext.android.viewModel

class MatchesFragment : BaseFragment<MatchesViewModel, FragmentMatchesBinding>() {

    override val viewModel: MatchesViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_matches
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    private lateinit var adapter: MatchesListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavigationBarVisibility(View.VISIBLE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        adapter = MatchesListAdapter {
            viewModel.openMatchResult(it)
        }

        matchesSearchResultsRecyclerView.adapter = adapter
        matchesSearchResultsRecyclerView.isNestedScrollingEnabled = true
        adapter.addScrollToTopListener(matchesSearchResultsRecyclerView)
    }

    private fun initObservers() {

        // filter matches when search text is updated
        viewModel.searchText.observe(this@MatchesFragment, Observer {
            viewModel.filterMatches()
        })

        // update adapter and empty state
        viewModel.matches.observe(this@MatchesFragment, Observer {
            adapter.submitList(it)
            if (it.isNullOrEmpty()) {
                emptyStateView.visibility = View.VISIBLE
            } else {
                emptyStateView.visibility = View.GONE
            }
        })
    }
}
