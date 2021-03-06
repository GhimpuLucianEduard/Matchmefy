package com.lucianghimpu.matchmefy.presentation.matchResult

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.lucianghimpu.matchmefy.MatchmefyApp
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.data.dataModels.Playlist
import com.lucianghimpu.matchmefy.data.dataModels.matchmefyAPI.MatchResult
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.CreatePlaylistRequest
import com.lucianghimpu.matchmefy.data.dataModels.spotifyAPI.EditPlaylistRequest
import com.lucianghimpu.matchmefy.data.dataServices.SpotifyService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialogListener
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialogListener
import com.lucianghimpu.matchmefy.utilities.ColoredTextSpan
import com.lucianghimpu.matchmefy.utilities.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MatchResultViewModel(
    application: Application,
    preferencesService: PreferencesService,
    private val spotifyService: SpotifyService
) : BaseViewModel(application, preferencesService) {

    private val context: Context = this.getApplication<MatchmefyApp>().applicationContext

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
            in 0..19 -> context.getString(R.string.match_score_0_title)
            in 20..39 -> context.getString(R.string.match_score_20_title)
            in 40..59 -> context.getString(R.string.match_score_40_title)
            in 60..79 -> context.getString(R.string.match_score_60_title)
            else -> context.getString(R.string.match_score_80_title)
        }
    }

    val matchImage = Transformations.map(matchResult) {
        when (it.matchingScore.toInt()) {
            in 0..19 -> context.getDrawable(R.drawable.match0)
            in 20..39 -> context.getDrawable(R.drawable.match20)
            in 40..59 -> context.getDrawable(R.drawable.match40)
            in 60..79 -> context.getDrawable(R.drawable.match60)
            else -> context.getDrawable(R.drawable.match80)
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
        val nextState = stateManager.next()
        AppAnalytics.trackLog("Switching to next state in MatchResult: $nextState")
        _state.value = nextState
    }

    fun onBackClicked() {
        val prevState = stateManager.prev()
        AppAnalytics.trackLog("Switching to prev state in MatchResult: $prevState")
        _state.value = prevState
    }

    fun onCreatePlaylistClicked() {
        AppAnalytics.trackEvent("create_playlist_clicked")
        if (matchResult.value!!.user.id == matchResult.value!!.matchingUser.id) {
            showDialog(SingleButtonDialog(
                title = context.getString(R.string.create_playlist_dialog_error_title),
                description = context.getString(R.string.create_playlist_dialog_error_description),
                imageId = R.drawable.dialog_warning,
                buttonText = context.getString(R.string.error_dialog_button),
                listener = object : SingleButtonDialogListener {
                    override fun onButtonClicked() {
                        AppAnalytics.trackEvent("create_playlist_self_error_clicked")
                    }
                }
            ))
        } else {
            showDialog(DoubleButtonDialog(
                title = context.getString(R.string.create_playlist_dialog_title),
                description = context.getString(R.string.create_playlist_dialog_description),
                descriptionSpan = ColoredTextSpan(26, 47),
                imageId = R.drawable.dialog_warning,
                positiveButtonText = context.getString(R.string.create_dialog_button),
                negativeButtonText = context.getString(R.string.cancel_dialog_button),
                listener = object : DoubleButtonDialogListener {
                    override fun onPositiveButtonClicked() {
                        AppAnalytics.trackEvent("create_playlist_confirmation_clicked")
                        createPlaylist()
                    }

                    override fun onNegativeButtonClicked() {
                        AppAnalytics.trackEvent("cancel_create_playlist")
                    }
                }
            ))
        }
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

                AppAnalytics.trackLog("Spotify playlist created")

                withContext(Dispatchers.IO) {
                    spotifyService.editPlaylist(playlist.id, EditPlaylistRequest(matchResult.value!!.playlistForSpotify.uris))
                }

                AppAnalytics.trackLog("Added tracks to Spotify playlist")

                hideLoadingDialog()
                onPlaylistCreated()

            } catch (ex: Exception) {
                hideLoadingDialog()
                handleError(ex)
            }
            // N.B. don't hide the loading dialog in a finally clause
        }
    }

    private fun onPlaylistCreated() {
        showDialog(DoubleButtonDialog(
            title = context.getString(R.string.playlist_created_dialog_title),
            description = context.getString(R.string.playlist_created_dialog_description),
            imageId = R.drawable.dialog_confirmation,
            positiveButtonText = context.getString(R.string.open_dialog_button),
            negativeButtonText = context.getString(R.string.later_dialog_button),
            listener = object : DoubleButtonDialogListener {
                override fun onPositiveButtonClicked() {
                    AppAnalytics.trackEvent("open_playlist_clicked")
                    _openPlaylistEvent.value = Event(playlist)
                }

                override fun onNegativeButtonClicked() {
                    AppAnalytics.trackEvent("cancel_open_playlist")
                    navigateToMatches()
                }
            }
        ))
    }

    fun navigateToMatches() {
        navigate(MatchResultFragmentDirections.actionMatchResultFragmentToMatchFragment())
    }
}
