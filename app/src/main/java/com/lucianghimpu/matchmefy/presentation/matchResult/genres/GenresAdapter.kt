package com.lucianghimpu.matchmefy.presentation.matchResult.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.LayoutGenresItemBinding

class GenresAdapter :
    ListAdapter<String, GenresAdapter.GenresViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder =
        GenresViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_genres_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(super.getItem(position)!!)
    }

    inner class GenresViewHolder(
        private val layoutGenresItemBinding: LayoutGenresItemBinding
    ) : RecyclerView.ViewHolder(layoutGenresItemBinding.root) {
        fun bind(genre: String) {
            layoutGenresItemBinding.genre = genre
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
