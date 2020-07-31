package com.lucianghimpu.matchmefy.presentation.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentUserPreviewBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_preview.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserPreviewFragment : BaseFragment<UserPreviewViewModel, FragmentUserPreviewBinding>() {
    private val args: UserPreviewFragmentArgs by navArgs()
    override val viewModel: UserPreviewViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_user_preview
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.setBottomNavigationBarVisibility(View.VISIBLE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initData(args.user)

        viewModel.isBusy.observe(this@UserPreviewFragment, Observer {
            if (it) {
                progressIndicator.show()
            } else {
                progressIndicator.hide()
            }
        })
    }
}
