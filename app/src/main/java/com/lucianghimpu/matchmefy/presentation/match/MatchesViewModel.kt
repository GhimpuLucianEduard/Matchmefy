package com.lucianghimpu.matchmefy.presentation.match

import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.presentation.BaseViewModel

class MatchesViewModel: BaseViewModel() {
    var matchSearchText = MutableLiveData<String>()
}
