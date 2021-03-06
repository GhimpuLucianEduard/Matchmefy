package com.lucianghimpu.matchmefy.presentation.matchResult.artists

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultArtistsBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.CarouselItem
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import com.lucianghimpu.matchmefy.utilities.DisplayUtil.Companion.pixelToDp
import com.lucianghimpu.matchmefy.utilities.extensions.disableScrollAnimation
import com.lucianghimpu.matchmefy.utilities.extensions.setShowSideItems
import kotlinx.android.synthetic.main.fragment_match_result_artists.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MatchResultArtistsFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultArtistsBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel(from = { parentFragment!! })
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_artists
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private lateinit var adapter: ArtistsCarouselAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArtistsCarouselAdapter()

        viewModel.matchResult.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result.matchingArtists.map {
                CarouselItem(
                    title = it.name,
                    imageUrl = it.images.firstOrNull()?.url
                )
            })
        })

        carousel.adapter = adapter
        carousel.disableScrollAnimation()
        val transformerSideMargin = pixelToDp(activity!!, resources.getDimension(R.dimen.image_carousel_margin) / 2)
        carousel.setShowSideItems(transformerSideMargin, transformerSideMargin)
    }
}

