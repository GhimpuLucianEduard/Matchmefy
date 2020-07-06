package com.lucianghimpu.matchmefy.presentation.login

import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AuthService
import com.lucianghimpu.matchmefy.databinding.FragmentLoginBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_login
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private val authService: AuthService by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            progressIndicator.visibility = View.VISIBLE
            authService.sendAuthCodeRequest(activity!!)
        }
    }
}
