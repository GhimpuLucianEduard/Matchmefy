package com.lucianghimpu.matchmefy.presentation.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.databinding.LayoutSearchListItemBinding
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG

class SearchListAdapter : PagedListAdapter<User, SearchListAdapter.SearchItemHolder>(
    diffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder =
        SearchItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_search_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchItemHolder, position: Int) {
        holder.bind(super.getItem(position)!!)
    }

    inner class SearchItemHolder(
        val searchItemBinding: LayoutSearchListItemBinding
    ) : RecyclerView.ViewHolder(searchItemBinding.root) {
        fun bind(user: User)  {
            searchItemBinding.user = user
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                Log.d(LOG_TAG, "areItemsTheSame")
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                Log.d(LOG_TAG, "areContentsTheSame")
                return oldItem == newItem
            }

        }
    }
}