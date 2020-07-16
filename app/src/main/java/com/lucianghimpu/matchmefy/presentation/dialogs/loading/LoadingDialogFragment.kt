package com.lucianghimpu.matchmefy.presentation.dialogs.loading

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentLoadingDialogBinding
import com.lucianghimpu.matchmefy.presentation.dialogs.BaseDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class LoadingDialogFragment : BaseDialogFragment<DialogViewModel<LoadingDialog>, FragmentLoadingDialogBinding, LoadingDialog>() {

    override val viewModel: DialogViewModel<LoadingDialog> by viewModel(named(DIConstants.LOADING_DIALOG))
    override fun getLayoutResId(): Int = R.layout.fragment_loading_dialog
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    private val args: LoadingDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(args.dialog)
    }
}