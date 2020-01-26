package com.lucianghimpu.matchmefy.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentLoginBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import com.lucianghimpu.matchmefy.presentation.services.SpotifyAuthService
import com.lucianghimpu.matchmefy.utilities.LogConstants.LOG_TAG
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_login
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private val spotifyAuthService: SpotifyAuthService by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(LOG_TAG, "Spotify version: ${com.spotify.sdk.android.auth.BuildConfig.VERSION_NAME}")

        loginButton.setOnClickListener {
            val request = spotifyAuthService.getAuthenticationRequest(AuthorizationResponse.Type.TOKEN)
            AuthorizationClient.openLoginActivity(activity, spotifyAuthService.AUTH_TOKEN_REQUEST_CODE, request)
        }
    }
}
