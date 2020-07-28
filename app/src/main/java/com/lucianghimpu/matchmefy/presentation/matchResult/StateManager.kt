package com.lucianghimpu.matchmefy.presentation.matchResult

import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import java.util.*

/**
 * Class used to manage the current State of the carousel view used to
 * display the match result information
 */
class StateManager(
    val matchResult: MatchResult
) {
    private val states = LinkedList<MatchResultState>()
    private var currentStateIndex: Int = 0

    var currentState: MatchResultState
        private set

    init {
        // build the linked list based on the available data
        states.add(MatchResultState.SCORE)

        if (matchResult.matchingArtists.any()) {
            states.add(MatchResultState.ARTISTS)
        }

        if (matchResult.matchingTracks.any()) {
            states.add(MatchResultState.TRACKS)
        }

        if (matchResult.matchingGenres.any()) {
            states.add(MatchResultState.GENRES)
        }

        states.add(MatchResultState.PLAYLIST)

        currentState = states[0]
    }

    fun size(): Int = states.size

    fun getByIndex(index: Int): MatchResultState = states[index]

    fun getIndexByState(state: MatchResultState): Int = states.indexOf(state)

    fun next(): MatchResultState {
        if (currentState == states.last) {
            return currentState
        }

        currentStateIndex++
        currentState = states[currentStateIndex]
        return currentState
    }

    fun prev(): MatchResultState {
        if (currentState == states.first) {
            return currentState
        }

        currentStateIndex--
        currentState = states[currentStateIndex]
        return currentState
    }
}