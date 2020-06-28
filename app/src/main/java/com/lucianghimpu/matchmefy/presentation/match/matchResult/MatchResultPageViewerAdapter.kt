package com.lucianghimpu.matchmefy.presentation.match.matchResult

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MatchResultPageViewerAdapter(
    fragment: MatchResultFragment,
    private val stateManager: StateManager
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return stateManager.size()
    }

    override fun createFragment(position: Int): Fragment {
        return when(stateManager.getByIndex(position)) {
            MatchResultState.SCORE -> MatchResultScoreFragment()
            MatchResultState.ARTISTS -> MatchResultArtistsFragment()
            MatchResultState.TRACKS -> MatchResultTracksFragment()
            MatchResultState.GENRES -> MatchResultGenresFragment()
        }
    }
}