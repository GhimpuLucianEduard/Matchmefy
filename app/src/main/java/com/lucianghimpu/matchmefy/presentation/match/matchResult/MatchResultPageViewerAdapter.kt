package com.lucianghimpu.matchmefy.presentation.match.matchResult

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IndexOutOfBoundsException

class MatchResultPageViewerAdapter(
    private val fragment: MatchResultFragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragment.getCarouselPageCount()
    }

    override fun createFragment(position: Int): Fragment = when(position) {
            0 -> MatchResultScoreFragment()
            1 -> MatchResultArtistsFragment()
            2 -> MatchResultTracksFragment()
            3 -> MatchResultGenresFragment()
            else -> throw IndexOutOfBoundsException()
        }
}