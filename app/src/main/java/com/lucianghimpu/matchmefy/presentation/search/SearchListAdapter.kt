package com.lucianghimpu.matchmefy.presentation.search

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.databinding.LayoutSearchListItemBinding

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.SearchItemHolder>() {

    private var items: List<User> = emptyList()

    fun setItems(data: List<User>) {
        items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder =
        SearchItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_search_list_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: SearchItemHolder, position: Int) {
        holder.searchItemBinding.user = items[position ]
    }

    inner class SearchItemHolder(
        val searchItemBinding: LayoutSearchListItemBinding
    ) : RecyclerView.ViewHolder(searchItemBinding.root)
}