package com.lucianghimpu.matchmefy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.appServices.AppAnalytics
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialogFragment
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialogFragment
import com.lucianghimpu.matchmefy.utilities.DialogTagsConstants.DOUBLE_BUTTON_DIALOG_TAG
import com.lucianghimpu.matchmefy.utilities.DialogTagsConstants.LOADING_DIALOG_TAG
import com.lucianghimpu.matchmefy.utilities.DialogTagsConstants.SINGLE_BUTTON_DIALOG_TAG
import com.lucianghimpu.matchmefy.utilities.EventObserver
import com.lucianghimpu.matchmefy.utilities.NavigationCommand

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected abstract val viewModel: VM
    protected lateinit var binding: DB

    protected lateinit var mainActivity: MainActivity

    private var displayedDialog: DialogFragment? = null

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    abstract fun setViewDataBindingViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding(inflater, container)
        setViewDataBindingViewModel()

        AppAnalytics.trackEvent("fragment_on_create", null, Bundle().also {
            it.putString("fragment", this::class.simpleName)
        })
        return binding.root
    }

    private fun showDialogFragment(dialogFragment: DialogFragment, tag: String) {
        AppAnalytics.trackLog("Showing dialog of type: $tag in ${this::class.simpleName}")
        displayedDialog = dialogFragment
        displayedDialog!!.show(this.fragmentManager!!, tag)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)

        viewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is NavigationCommand.To -> navController.navigate(it.directions)
                is NavigationCommand.Back -> navController.navigateUp()
            }
        })

        viewModel.showDialogEvent.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is SingleButtonDialog -> showDialogFragment(SingleButtonDialogFragment(it), SINGLE_BUTTON_DIALOG_TAG)
                is LoadingDialog -> showDialogFragment(LoadingDialogFragment(it), LOADING_DIALOG_TAG)
                is DoubleButtonDialog -> showDialogFragment(DoubleButtonDialogFragment(it), DOUBLE_BUTTON_DIALOG_TAG)
            }
        })

        viewModel.hideDialogEvent.observe(viewLifecycleOwner, EventObserver {
            if (displayedDialog != null) {
                displayedDialog!!.dismiss()
            }
        })

        mainActivity = activity as MainActivity
        mainActivity.setBottomNavigationBarVisibility(View.GONE)
    }
}
