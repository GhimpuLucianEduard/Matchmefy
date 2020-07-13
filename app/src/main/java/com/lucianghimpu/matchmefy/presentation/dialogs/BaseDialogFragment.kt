package com.lucianghimpu.matchmefy.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.presentation.MainActivity
import com.lucianghimpu.matchmefy.utilities.EventObserver
import com.lucianghimpu.matchmefy.utilities.NavigationCommand

/**
 * Basically the same as [com.lucianghimpu.matchmefy.presentation.BaseFragment]
 *
 * But as multiple inheritance is not supported in kotlin
 * this is the simple way to also provide mvvm support to [DialogFragment]s
 *
 */
abstract class BaseDialogFragment<VM : DialogViewModel<T>, DB : ViewDataBinding, T : Dialog> : DialogFragment() {

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
        mainActivity = activity as MainActivity
        mainActivity.setBottomNavigationBarVisibility(View.GONE)
    }
}