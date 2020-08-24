package com.lucianghimpu.matchmefy.presentation.dialogs.noConnection

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentNoConnectionDialogBinding
import com.lucianghimpu.matchmefy.presentation.dialogs.BaseDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.DialogViewModel
import com.lucianghimpu.matchmefy.utilities.DIConstants
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class NoConnectionDialogFragment(
    private val noConnectionDialog: NoConnectionDialog = NoConnectionDialog()
) : BaseDialogFragment<DialogViewModel<NoConnectionDialog>, FragmentNoConnectionDialogBinding, NoConnectionDialog>() {

    override val viewModel: DialogViewModel<NoConnectionDialog> by viewModel(named(DIConstants.NO_CONNECTION_DIALOG))
    override fun getLayoutResId(): Int = R.layout.fragment_no_connection_dialog
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
            }
        }
    }

}