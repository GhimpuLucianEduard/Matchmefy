package com.lucianghimpu.matchmefy.presentation.match.matchResult

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentMatchResultArtistsBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_match_result_artists.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MatchResultArtistsFragment : BaseFragment<MatchResultViewModel, FragmentMatchResultArtistsBinding>() {
    override val viewModel: MatchResultViewModel by sharedViewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_match_result_artists
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private lateinit var adapter: ImageCarouselAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ImageCarouselAdapter(emptyList())

        viewModel.matchingArtists.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list.map {
                CarouselItem(it.name, it.images.first().url)
            })
        })

        carousel.adapter = adapter
    }
}