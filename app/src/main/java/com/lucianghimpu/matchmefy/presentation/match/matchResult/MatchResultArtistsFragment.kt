package com.lucianghimpu.matchmefy.presentation.match.matchResult

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultArtistsBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.utilities.DensityUtil.Companion.pixelToDp
import com.lucianghimpu.matchmefy.utilities.Extensions.setShowSideItems
import kotlinx.android.synthetic.main.fragment_match_result_artists.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MatchResultArtistsFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultArtistsBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_artists
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private lateinit var adapter: ImageCarouselAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ImageCarouselAdapter()

        viewModel.matchingArtists.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list.map {
                CarouselItem(it.name, it.images.first().url)
            })
        })

        carousel.adapter = adapter
        val transformerSideMargin = pixelToDp(activity!!, resources.getDimension(R.dimen.image_carousel_margin) / 2)
        carousel.setShowSideItems(transformerSideMargin, transformerSideMargin)
    }
}

