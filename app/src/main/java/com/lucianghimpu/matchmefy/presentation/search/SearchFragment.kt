package com.lucianghimpu.matchmefy.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentSearchBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.lucianghimpu.matchmefy.utilities.NetworkState
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.progressIndicator
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override val viewModel: SearchViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_search
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private lateinit var adapter: SearchListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavigationBarVisibility(View.VISIBLE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchListAdapter()
        searchResultsRecyclerView.adapter = adapter
        searchResultsRecyclerView.isNestedScrollingEnabled = true
        viewModel.users.observe(this@SearchFragment, Observer {
            adapter.submitList(it)
            Log.d(LOG_TAG, "submit list with count ${it.size}")
//            if (it.isNotEmpty()) {
//                emptyState.visibility = View.GONE
//            } else {
//                emptyState.visibility = View.VISIBLE
//            }
        })

//        viewModel.searchText.observe(this@SearchFragment, Observer {
//            viewModel.getSearchResults(it)
//        })

        viewModel.networkState?.observe(this@SearchFragment, Observer {
            if (it == NetworkState.RUNNING) {
                progressIndicator.visibility = View.VISIBLE
            } else {
                progressIndicator.visibility = View.GONE
            }
        })

        searchEditText.addTextChangedListener {
            viewModel.getSearchResults(it.toString())
        }
    }
}
