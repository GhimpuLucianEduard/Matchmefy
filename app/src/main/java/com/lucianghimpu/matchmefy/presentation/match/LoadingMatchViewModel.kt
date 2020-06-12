package com.lucianghimpu.matchmefy.presentation.match

import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.data.dataModels.Artist
import com.lucianghimpu.matchmefy.data.dataModels.Track
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel

class LoadingMatchViewModel : BaseViewModel() {
    var userProfile =  MutableLiveData<User>()
    lateinit var userArtist: List<Artist>
    lateinit var userTracks: List<Track>

    fun getMatchData() {

    }
}
