package com.lucianghimpu.matchmefy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.utilities.Event
import com.lucianghimpu.matchmefy.utilities.EventObserver

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected abstract val viewModel: VM
    protected lateinit var binding: DB

    fun initBinding(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    abstract fun setViewDataBindingViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding(inflater, container!!)
        setViewDataBindingViewModel()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(it)
        })
    }
}
