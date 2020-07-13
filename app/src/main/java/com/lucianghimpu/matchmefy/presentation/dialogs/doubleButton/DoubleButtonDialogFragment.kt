package com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentDoubleButtonDialogBinding
import com.lucianghimpu.matchmefy.presentation.dialogs.BaseDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DoubleButtonDialogFragment : BaseDialogFragment<DialogViewModel<DoubleButtonDialog>,
        FragmentDoubleButtonDialogBinding, DoubleButtonDialog>() {

    override val viewModel: DialogViewModel<DoubleButtonDialog> by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_double_button_dialog
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    private val args: DoubleButtonDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(args.dialog)
    }
}