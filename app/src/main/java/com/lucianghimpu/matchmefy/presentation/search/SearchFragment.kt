package com.lucianghimpu.matchmefy.presentation.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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

        adapter = SearchListAdapter()
        searchResultsRecyclerView.adapter = adapter
        viewModel.users.observe(this@SearchFragment, Observer {
            adapter.setItems(it)
//            if (it.isNotEmpty()) {
//                emptyState.visibility = View.GONE
//            } else {
//                emptyState.visibility = View.VISIBLE
//            }
        })
    }
}
