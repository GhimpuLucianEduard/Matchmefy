package com.lucianghimpu.matchmefy.presentation.matchResult

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

    lateinit var stateManager: StateManager
        private set

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

    val matchImage = Transformations.map(matchResult) {
        when (it.matchingScore.toInt()) {
            in 0..19 -> resourceProvider.getDrawable(R.drawable.match0)
            in 20..39 -> resourceProvider.getDrawable(R.drawable.match20)
            in 40..59 -> resourceProvider.getDrawable(R.drawable.match40)
            in 60..79 -> resourceProvider.getDrawable(R.drawable.match60)
            else -> resourceProvider.getDrawable(R.drawable.match80)
        }
    }

    val isBackButtonVisible: LiveData<Boolean> = Transformations.map(_state) {
        it != MatchResultState.SCORE
    }

    fun initData(newMatchResult: MatchResult) {
        stateManager = StateManager(newMatchResult)
        matchResult.value = newMatchResult
        _state.value = stateManager.currentState
    }

    fun onContinueClicked() {
        _state.value = stateManager.next()
    }

    fun onBackClicked() {
        _state.value = stateManager.prev()
    }
}
