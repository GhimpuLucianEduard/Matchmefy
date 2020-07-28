package com.lucianghimpu.matchmefy.presentation.dialogs.loading

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentLoadingDialogBinding
import com.lucianghimpu.matchmefy.presentation.dialogs.BaseDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


class LoadingDialogFragment(
    private val loadingDialog: LoadingDialog
) : BaseDialogFragment<DialogViewModel<LoadingDialog>, FragmentLoadingDialogBinding, LoadingDialog>() {

    override val viewModel: DialogViewModel<LoadingDialog> by viewModel(named(DIConstants.LOADING_DIALOG))
    override fun getLayoutResId(): Int = R.layout.fragment_loading_dialog
    override fun setViewDataBindingViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                // Disable back button for loading fragments
                // It's ok as long as this kind of fragment is used for http requests
                // which usually have a timeout, so the dialog should be dismissed
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(loadingDialog)
    }
}