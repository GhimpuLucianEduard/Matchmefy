package com.lucianghimpu.matchmefy.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.databinding.FragmentSearchBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.progressIndicator
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.Console

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
            if (it != null) {
                Log.d(LOG_TAG, "submit list with count ${it.size} on thread: ${Thread.currentThread().name}")
            } else {
                Log.d(LOG_TAG, "null")
            }
        })


//        viewModel.searchText.observe(this@SearchFragment, Observer {
//            viewModel.getSearchResults(it)
//        })

        searchEditText.doOnTextChanged { text, start, count, after ->
            viewModel.getSearchResults(text.toString())
        }

//        viewModel.networkState?.observe(this@SearchFragment, Observer {
//            Log.d(LOG_TAG, "Network state for progress idnicator: ${it}")
//            if (it == NetworkState.RUNNING) {
//                progressIndicator.show()
//                Log.d(LOG_TAG, "SHOW")
//            } else {
//                progressIndicator.hide()
//                Log.d(LOG_TAG, "HIDE")
//            }
//        })
    }
}
