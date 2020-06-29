package com.lucianghimpu.matchmefy.presentation.match.matchResult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.LayoutCarouselItemBinding

class ImageCarouselAdapter :
    ListAdapter<CarouselItem, ImageCarouselAdapter.ImageCarouselViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCarouselViewHolder =
        ImageCarouselViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_carousel_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageCarouselViewHolder, position: Int) {
        holder.bind(super.getItem(position)!!)
    }

    inner class ImageCarouselViewHolder(
        private val layoutCarouselItemBinding: LayoutCarouselItemBinding
    ) : RecyclerView.ViewHolder(layoutCarouselItemBinding.root) {
        fun bind(carouselItem: CarouselItem) {
            layoutCarouselItemBinding.carouselItem = carouselItem
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CarouselItem>() {
            override fun areItemsTheSame(oldItem: CarouselItem, newItem: CarouselItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: CarouselItem, newItem: CarouselItem): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}