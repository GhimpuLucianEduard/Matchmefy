package com.lucianghimpu.matchmefy.presentation.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentSearchBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
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

        adapter = SearchListAdapter {
            viewModel.onSearchResultClicked(it)
        }

        searchResultsRecyclerView.adapter = adapter
        searchResultsRecyclerView.isNestedScrollingEnabled = true
        viewModel.users.observe(this@SearchFragment, Observer {
            adapter.submitList(it)

            if (it == null || it.isEmpty()) {
                if (!progressIndicator.isVisible) {
                    emptyStateView.visibility = View.VISIBLE
                }
            } else {
                emptyStateView.visibility = View.GONE
            }
        })

        // TODO: not sure about this, it works but I don't like the code
        // need to do a little bit of testing to find a more concise way of writing this
        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                searchResultsRecyclerView.scrollToPosition(0)
            }
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                searchResultsRecyclerView.scrollToPosition(0)
            }
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                searchResultsRecyclerView.scrollToPosition(0)
            }
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                searchResultsRecyclerView.scrollToPosition(0)
            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                searchResultsRecyclerView.scrollToPosition(0)
            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                searchResultsRecyclerView.scrollToPosition(0)
            }
        })

        viewModel.searchText.observe(this@SearchFragment, Observer {
            viewModel.getSearchResults()
        })

        viewModel.isBusy.observe(this@SearchFragment, Observer {
            if (it) {
                progressIndicator.show()
            } else {
                progressIndicator.hide()
            }
        })
    }
}
