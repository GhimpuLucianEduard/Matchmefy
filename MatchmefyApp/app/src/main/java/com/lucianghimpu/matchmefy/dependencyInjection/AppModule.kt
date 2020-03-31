package com.lucianghimpu.matchmefy.dependencyInjection

import com.lucianghimpu.matchmefy.presentation.SharedViewModel
import com.lucianghimpu.matchmefy.presentation.login.LoginViewModel
import com.lucianghimpu.matchmefy.services.EncryptedSharedPreferencesServiceImpl
import com.lucianghimpu.matchmefy.services.SpotifyAuthService
import com.lucianghimpu.matchmefy.presentation.welcome.WelcomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // TODO: move these in a Presentation Module
    single { SharedViewModel(get(), get(), get()) }
    viewModel { LoginViewModel() }
    viewModel { WelcomeViewModel() }
}