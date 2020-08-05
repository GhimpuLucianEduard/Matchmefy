package com.lucianghimpu.matchmefy.presentation.matchResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAuthService
import com.lucianghimpu.matchmefy.appServices.ResourceProvider
import com.lucianghimpu.matchmefy.data.dataModels.Playlist
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.CreatePlaylistRequest
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.EditPlaylistRequest
import com.lucianghimpu.matchmefy.data.dataServices.SpotifyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialogListener
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MatchResultViewModel(
    private val resourceProvider: ResourceProvider,
    private val spotifyService: SpotifyService,
    private val appAuthService: AppAuthService
) : BaseViewModel() {

    lateinit var playlist: Playlist

    lateinit var stateManager: StateManager
        private set

    var matchResult = MutableLiveData<MatchResult>()

    private var _state = MutableLiveData<MatchResultState>()
    val state: LiveData<MatchResultState>
        get() = _state

    private var _openPlaylistEvent = MutableLiveData<Event<Playlist>>()
    val openPlaylistEvent: LiveData<Event<Playlist>>
        get() = _openPlaylistEvent

    val stateIndex: LiveData<Int> = Transformations.map(state) {
        stateManager.getIndexByState(it)
    }

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

    // TODO: move to xml
    val isBackButtonVisible: LiveData<Boolean> = Transformations.map(_state) {
        it != MatchResultState.SCORE
    }

    fun initData(newMatchResult: MatchResult) {
        stateManager = StateManager(newMatchResult)
        matchResult.value = newMatchResult
        _state.value = stateManager.currentState
    }

    fun onContinueClicked() {
        val nextState = stateManager.next()
        Log.i(LOG_TAG, "Switching to next state in MatchResult: $nextState")
        _state.value = nextState
    }

    fun onBackClicked() {
        val prevState = stateManager.prev()
        Log.i(LOG_TAG, "Switching to prev state in MatchResult: $prevState")
        _state.value = prevState
    }

    fun onCreatePlaylistClicked() {
        showDialog(DoubleButtonDialog(
            title = resourceProvider.getString(R.string.create_playlist_dialog_title),
            description = resourceProvider.getString(R.string.create_playlist_dialog_description),
            descriptionSpan = ColoredTextSpan(26, 47),
            imageId = R.drawable.dialog_warning,
            positiveButtonText = resourceProvider.getString(R.string.create_dialog_button),
            negativeButtonText = resourceProvider.getString(R.string.cancel_dialog_button),
            listener = object : DoubleButtonDialogListener {
                override fun onPositiveButtonClicked() {
                    Log.i(LOG_TAG, "User selected create playlist")
                    hideDialog()
                    createPlaylist()
                }

                override fun onNegativeButtonClicked() {
                    Log.i(LOG_TAG, "User canceled create playlist")
                    hideDialog()
                }
            }
        ))
    }

    private fun createPlaylist() {
        showDialog(LoadingDialog())
        viewModelScope.launch {
            val createPlaylistRequest = CreatePlaylistRequest(
                matchResult.value!!.playlistForSpotify.name,
                matchResult.value!!.playlistForSpotify.description
            )

            try {
                playlist = withContext(Dispatchers.IO) {
                    spotifyService.createPlaylist(createPlaylistRequest)
                }

                Log.i(LOG_TAG, "Spotify Playlist created!")

                withContext(Dispatchers.IO) {
                    spotifyService.editPlaylist(playlist.id, EditPlaylistRequest(matchResult.value!!.playlistForSpotify.uris))
                }

                Log.i(LOG_TAG, "Spotify Playlist added tracks!")
                hideDialog()
                onPlaylistCreated()

            } catch (exception: Exception) {
                hideDialog()
                Log.e(LOG_TAG, "Error creating Spotify Playlist: $exception")
            }
            // N.B. don't hide the loading dialog in a finally clause
        }
    }

    private fun onPlaylistCreated() {
        showDialog(DoubleButtonDialog(
            title = resourceProvider.getString(R.string.playlist_created_dialog_title),
            description = resourceProvider.getString(R.string.playlist_created_dialog_description),
            imageId = R.drawable.dialog_confirmation,
            positiveButtonText = resourceProvider.getString(R.string.open_dialog_button),
            negativeButtonText = resourceProvider.getString(R.string.later_dialog_button),
            listener = object : DoubleButtonDialogListener {
                override fun onPositiveButtonClicked() {
                    Log.i(LOG_TAG, "User selected open playlist in Spotify")
                    _openPlaylistEvent.value = Event(playlist)
                    hideDialog()
                }

                override fun onNegativeButtonClicked() {
                    Log.i(LOG_TAG, "User declined open playlist in Spotify")
                    hideDialog()
                    navigateToMatches()
                }
            }
        ))
    }

    fun navigateToMatches() {
        navigate(MatchResultFragmentDirections.actionMatchResultFragmentToMatchFragment())
    }
}
