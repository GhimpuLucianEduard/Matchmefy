package com.lucianghimpu.matchmefy.presentation.match.matchResult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.presentation.BaseViewModel

class MatchResultViewModel : BaseViewModel() {

    private var _state = MutableLiveData<MatchResultState>()
    val state: LiveData<MatchResultState>
        get() = _state

    init {
        _state.value = MatchResultState.SCORE
    }

    fun onContinueClicked() {
        if (_state.value == MatchResultState.GENRES) {
            return
        }

        val values = MatchResultState.values()
        val nextOrdinal = (_state.value!!.ordinal + 1)
        _state.postValue(values[nextOrdinal])
    }

    enum class MatchResultState {
        SCORE,
        ARTISTS,
        TRACKS,
        GENRES
    }
}
