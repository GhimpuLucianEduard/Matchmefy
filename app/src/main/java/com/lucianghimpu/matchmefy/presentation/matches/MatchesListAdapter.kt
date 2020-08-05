package com.lucianghimpu.matchmefy.presentation.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.databinding.LayoutMatchesSeachListItemBinding

class MatchesListAdapter(
    val clickListener: (MatchResult) -> Unit
) : ListAdapter<MatchResult, MatchesListAdapter.MatchItemHolder>(
    diffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchItemHolder =
        MatchItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_matches_seach_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MatchItemHolder, position: Int) {
        holder.bind(super.getItem(position)!!)
    }

    inner class MatchItemHolder(
        private val matchesItemBinding: LayoutMatchesSeachListItemBinding
    ) : RecyclerView.ViewHolder(matchesItemBinding.root) {
        fun bind(matchResult: MatchResult) {
            matchesItemBinding.matchResult = matchResult
            matchesItemBinding.root.setOnClickListener { clickListener(matchResult) }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MatchResult>() {
            override fun areItemsTheSame(oldItem: MatchResult, newItem: MatchResult): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: MatchResult, newItem: MatchResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}