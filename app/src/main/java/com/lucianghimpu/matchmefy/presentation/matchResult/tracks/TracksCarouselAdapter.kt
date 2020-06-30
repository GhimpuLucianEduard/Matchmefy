package com.lucianghimpu.matchmefy.presentation.matchResult.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.LayoutCarouselArtistItemBinding
import com.lucianghimpu.matchmefy.databinding.LayoutCarouselTrackItemBinding
import com.lucianghimpu.matchmefy.presentation.matchResult.CarouselItem

class TracksCarouselAdapter :
    ListAdapter<CarouselItem, TracksCarouselAdapter.TrackCarouselViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackCarouselViewHolder =
        TrackCarouselViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_carousel_track_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TrackCarouselViewHolder, position: Int) {
        holder.bind(super.getItem(position)!!)
    }

    inner class TrackCarouselViewHolder(
        private val layoutCarouselTrackItemBinding: LayoutCarouselTrackItemBinding
    ) : RecyclerView.ViewHolder(layoutCarouselTrackItemBinding.root) {
        fun bind(carouselItem: CarouselItem) {
            layoutCarouselTrackItemBinding.carouselItem = carouselItem
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