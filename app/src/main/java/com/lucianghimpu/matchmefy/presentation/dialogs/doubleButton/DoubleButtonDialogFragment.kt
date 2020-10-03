package com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton

import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentDoubleButtonDialogBinding
import com.lucianghimpu.matchmefy.presentation.dialogs.BaseDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants
import kotlinx.android.synthetic.main.fragment_double_button_dialog.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DoubleButtonDialogFragment(
    private val doubleButtonDialog: DoubleButtonDialog
) : BaseDialogFragment<DialogViewModel<DoubleButtonDialog>,
        FragmentDoubleButtonDialogBinding, DoubleButtonDialog>() {

    override val viewModel: DialogViewModel<DoubleButtonDialog> by viewModel(named(DIConstants.DOUBLE_BUTTON_DIALOG))
    override fun getLayoutResId(): Int = R.layout.fragment_double_button_dialog
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(doubleButtonDialog)

        negativeButton.setOnClickListener {
            viewModel.dialog.value?.listener?.onNegativeButtonClicked()
            dismiss()
        }

        positiveButton.setOnClickListener {
            viewModel.dialog.value?.listener?.onPositiveButtonClicked()
            dismiss()
        }
    }
}