package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.presentation.SharedViewModel
import com.lucianghimpu.matchmefy.presentation.login.LoginViewModel
import com.lucianghimpu.matchmefy.presentation.matchResult.MatchResultViewModel
import com.lucianghimpu.matchmefy.presentation.match.MatchViewModel
import com.lucianghimpu.matchmefy.presentation.search.SearchViewModel
import com.lucianghimpu.matchmefy.presentation.search.UserPreviewViewModel
import com.lucianghimpu.matchmefy.presentation.welcome.WelcomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // TODO: move these in a Presentation Module

    // Used by MainActivity and other fragments
    single { SharedViewModel(get(), get(), get()) }

    viewModel { LoginViewModel() }
    viewModel { WelcomeViewModel() }
    viewModel { SearchViewModel(get())}
    viewModel { MatchViewModel() }
    viewModel { UserPreviewViewModel(get()) }

    // Used by the Match Result Fragments
    single { MatchResultViewModel(get()) }
}