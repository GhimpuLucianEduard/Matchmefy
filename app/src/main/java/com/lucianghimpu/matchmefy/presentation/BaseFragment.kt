package com.lucianghimpu.matchmefy.presentation

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.lucianghimpu.matchmefy.NavigationGraphDirections
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.presentation.dialogs.doubleButton.DoubleButtonDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialog
import com.lucianghimpu.matchmefy.presentation.dialogs.singleButton.SingleButtonDialog
import com.lucianghimpu.matchmefy.utilities.EventObserver
import com.lucianghimpu.matchmefy.utilities.NavigationCommand

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected abstract val viewModel: VM
    protected lateinit var binding: DB

    protected lateinit var mainActivity: MainActivity

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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is NavigationCommand.To -> Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigate(it.directions)
                is NavigationCommand.Back -> Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigateUp()
            }

        })

        viewModel.showDialogEvent.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is SingleButtonDialog -> Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigate(NavigationGraphDirections.actionToSingleButtonDialogFragment(it))
                is LoadingDialog -> Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigate(NavigationGraphDirections.actionToLoadingDialogFragment(it))
                is DoubleButtonDialog -> Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigate(NavigationGraphDirections.actionToDoubleButtonDialogFragment(it))
            }
        })

        mainActivity = activity as MainActivity
        mainActivity.setBottomNavigationBarVisibility(View.GONE)
    }
}
