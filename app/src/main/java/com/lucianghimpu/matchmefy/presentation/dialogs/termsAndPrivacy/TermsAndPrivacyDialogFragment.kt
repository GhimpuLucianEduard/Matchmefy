package com.lucianghimpu.matchmefy.presentation.dialogs.termsAndPrivacy

import android.os.Bundle
import android.view.View
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentTermsAndPrivacyDialogBinding
import com.lucianghimpu.matchmefy.presentation.dialogs.BaseDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants
import kotlinx.android.synthetic.main.fragment_double_button_dialog.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class TermsAndPrivacyDialogFragment(
    private val termsAndPrivacyDialog: TermsAndPrivacyDialog
) : BaseDialogFragment<DialogViewModel<TermsAndPrivacyDialog>, FragmentTermsAndPrivacyDialogBinding, TermsAndPrivacyDialog>() {

    override val viewModel: DialogViewModel<TermsAndPrivacyDialog> by viewModel(named(DIConstants.TERMS_AND_PRIVACY_DIALOG))
    override fun getLayoutResId(): Int = R.layout.fragment_terms_and_privacy_dialog
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(termsAndPrivacyDialog)

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