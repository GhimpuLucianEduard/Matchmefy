package com.lucianghimpu.matchmefy.presentation.dialogs.singleButton

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentSingleButtonDialogBinding
import com.lucianghimpu.matchmefy.presentation.dialogs.BaseDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SingleButtonDialogFragment : BaseDialogFragment<DialogViewModel<SingleButtonDialog>,
        FragmentSingleButtonDialogBinding, SingleButtonDialog>() {

    override val viewModel: DialogViewModel<SingleButtonDialog> by viewModel(named(DIConstants.SINGLE_BUTTON_DIALOG))
    override fun getLayoutResId(): Int = R.layout.fragment_single_button_dialog
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    private val args: SingleButtonDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(args.dialog)
    }
}