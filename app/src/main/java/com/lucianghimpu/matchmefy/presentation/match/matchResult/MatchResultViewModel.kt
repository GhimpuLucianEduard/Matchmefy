package com.lucianghimpu.matchmefy.presentation.match.matchResult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.services.ResourceProvider

class MatchResultViewModel(
    resourceProvider: ResourceProvider
) : BaseViewModel() {

    var matchResult = MutableLiveData<MatchResult>()

    private var _state = MutableLiveData<MatchResultState>()
    val state: LiveData<MatchResultState>
        get() = _state

    val matchScoreTitle = Transformations.map(matchResult) {
        when (it.matchingScore.toInt()) {
            in 0..19 -> resourceProvider.getString(R.string.match_score_0_title)
            in 20..39 -> resourceProvider.getString(R.string.match_score_20_title)
            in 40..59 -> resourceProvider.getString(R.string.match_score_40_title)
            in 60..79 -> resourceProvider.getString(R.string.match_score_60_title)
            else -> resourceProvider.getString(R.string.match_score_80_title)
        }
    }

    val matchScoreSubtitle = Transformations.map(matchResult) {
        resourceProvider.getString(R.string.match_score_subtitle, it.matchingScore.toFloat())
    }

    val matchImage = Transformations.map(matchResult) {
        when (it.matchingScore.toInt()) {
            in 0..19 -> resourceProvider.getDrawable(R.drawable.match0)
            in 20..39 -> resourceProvider.getDrawable(R.drawable.match20)
            in 40..59 -> resourceProvider.getDrawable(R.drawable.match40)
            in 60..79 -> resourceProvider.getDrawable(R.drawable.match60)
            else -> resourceProvider.getDrawable(R.drawable.match80)
        }
    }

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

    fun onBackClicked() {
        if (_state.value == MatchResultState.SCORE) {
            return
        }

        val values = MatchResultState.values()
        val prevOrdinal = (_state.value!!.ordinal - 1)
        _state.postValue(values[prevOrdinal])
    }

    enum class MatchResultState {
        SCORE,
        ARTISTS,
        TRACKS,
        GENRES
    }
}
