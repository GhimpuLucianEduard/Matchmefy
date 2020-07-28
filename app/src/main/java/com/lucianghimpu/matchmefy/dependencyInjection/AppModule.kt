package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.presentation.SharedViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialog
import com.lucianghimpu.matchmefy.presentation.login.LoginViewModel
import com.lucianghimpu.matchmefy.presentation.match.MatchViewModel
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import com.lucianghimpu.matchmefy.presentation.search.SearchViewModel
import com.lucianghimpu.matchmefy.presentation.search.UserPreviewViewModel
import com.lucianghimpu.matchmefy.presentation.settings.SettingsViewModel
import com.lucianghimpu.matchmefy.presentation.welcome.WelcomeViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants.DOUBLE_BUTTON_DIALOG
import com.lucianghimpu.matchmefy.utilities.DIConstants.LOADING_DIALOG
import com.lucianghimpu.matchmefy.utilities.DIConstants.SINGLE_BUTTON_DIALOG
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    // TODO: move these in a Presentation Module

    // Used by MainActivity and other fragments
    single { SharedViewModel(get(), get(), get(), get()) }

    viewModel { LoginViewModel() }
    viewModel { WelcomeViewModel() }
    viewModel { SearchViewModel(get())}
    viewModel { MatchViewModel() }
    viewModel { UserPreviewViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }

    // Used by the Match Result Fragments
    viewModel { MatchResultViewModel(get(), get(), get()) }

    // Dialogs
    viewModel(named(SINGLE_BUTTON_DIALOG)) { DialogViewModel<SingleButtonDialog>() }
    viewModel(named(LOADING_DIALOG)) { DialogViewModel<LoadingDialog>() }
    viewModel(named(DOUBLE_BUTTON_DIALOG)) { DialogViewModel<DoubleButtonDialog>() }
}