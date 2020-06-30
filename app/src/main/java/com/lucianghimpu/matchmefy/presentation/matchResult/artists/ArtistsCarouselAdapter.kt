package com.lucianghimpu.matchmefy.presentation.matchResult.artists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.LayoutCarouselArtistItemBinding
import com.lucianghimpu.matchmefy.presentation.matchResult.CarouselItem

class ArtistsCarouselAdapter :
    ListAdapter<CarouselItem, ArtistsCarouselAdapter.ArtistCarouselViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistCarouselViewHolder =
        ArtistCarouselViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_carousel_artist_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ArtistCarouselViewHolder, position: Int) {
        holder.bind(super.getItem(position)!!)
    }

    inner class ArtistCarouselViewHolder(
        private val layoutCarouselArtistItemBinding: LayoutCarouselArtistItemBinding
    ) : RecyclerView.ViewHolder(layoutCarouselArtistItemBinding.root) {
        fun bind(carouselItem: CarouselItem) {
            layoutCarouselArtistItemBinding.carouselItem = carouselItem
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CarouselItem>() {
            override fun areItemsTheSame(oldItem: CarouselItem, newItem: CarouselItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: CarouselItem, newItem: CarouselItem): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}