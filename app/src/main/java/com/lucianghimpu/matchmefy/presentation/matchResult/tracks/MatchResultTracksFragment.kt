package com.lucianghimpu.matchmefy.presentation.matchResult.tracks

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultTracksBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.CarouselItem
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import com.lucianghimpu.matchmefy.utilities.DensityUtil
import com.lucianghimpu.matchmefy.utilities.Extensions.setShowSideItems
import kotlinx.android.synthetic.main.fragment_match_result_artists.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MatchResultTracksFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultTracksBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_tracks
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private lateinit var adapter: TracksCarouselAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TracksCarouselAdapter()

        viewModel.matchResult.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result.matchingTracks.map {
                CarouselItem(
                    title = it.name,
                    subtitle = it.artists.joinToString { artist ->
                        artist.name
                    },
                    imageUrl = it.album.images.first().url
                )
            })
        })

        carousel.adapter = adapter
        val transformerSideMargin = DensityUtil.pixelToDp(
            activity!!,
            resources.getDimension(R.dimen.image_carousel_margin) / 2
        )
        carousel.setShowSideItems(transformerSideMargin, transformerSideMargin)
    }
}