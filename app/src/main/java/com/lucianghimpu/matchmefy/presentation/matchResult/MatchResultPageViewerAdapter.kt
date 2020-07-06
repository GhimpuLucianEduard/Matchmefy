package com.lucianghimpu.matchmefy.presentation.matchResult

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lucianghimpu.matchmefy.presentation.matchResult.artists.MatchResultArtistsFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.genres.MatchResultGenresFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.playlist.MatchResultPlaylistFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.score.MatchResultScoreFragment
import com.lucianghimpu.matchmefy.presentation.matchResult.tracks.MatchResultTracksFragment

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
            MatchResultState.PLAYLIST -> MatchResultPlaylistFragment()
        }
    }
}