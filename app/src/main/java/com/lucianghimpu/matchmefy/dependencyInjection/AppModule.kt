package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.presentation.MainActivityViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.noConnection.NoConnectionDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialog
import com.lucianghimpu.matchmefy.presentation.login.LoginViewModel
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import com.lucianghimpu.matchmefy.presentation.matches.MatchesViewModel
import com.lucianghimpu.matchmefy.presentation.search.SearchViewModel
import com.lucianghimpu.matchmefy.presentation.search.UserPreviewViewModel
import com.lucianghimpu.matchmefy.presentation.settings.SettingsViewModel
import com.lucianghimpu.matchmefy.presentation.welcome.WelcomeViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants.DOUBLE_BUTTON_DIALOG
import com.lucianghimpu.matchmefy.utilities.DIConstants.LOADING_DIALOG
import com.lucianghimpu.matchmefy.utilities.DIConstants.NO_CONNECTION_DIALOG
import com.lucianghimpu.matchmefy.utilities.DIConstants.SINGLE_BUTTON_DIALOG
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    // TODO: move these in a Presentation Module

    // Used by MainActivity and other fragments
    viewModel { MainActivityViewModel(get(), get(), get()) }

    viewModel { LoginViewModel(get(), get(), get(), get(), get()) }
    viewModel { WelcomeViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get())}
    viewModel { MatchesViewModel(get(), get(), get()) }
    viewModel { UserPreviewViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }

    // Used by the Match Result Fragments
    viewModel { MatchResultViewModel(get(), get(), get()) }

    // Dialogs
    viewModel(named(SINGLE_BUTTON_DIALOG)) { DialogViewModel<SingleButtonDialog>(get()) }
    viewModel(named(LOADING_DIALOG)) { DialogViewModel<LoadingDialog>(get()) }
    viewModel(named(DOUBLE_BUTTON_DIALOG)) { DialogViewModel<DoubleButtonDialog>(get()) }
    viewModel(named(NO_CONNECTION_DIALOG)) { DialogViewModel<NoConnectionDialog>(get()) }
}