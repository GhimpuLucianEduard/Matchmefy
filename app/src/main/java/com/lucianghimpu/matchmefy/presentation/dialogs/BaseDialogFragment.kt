package com.lucianghimpu.matchmefy.presentation.dialogs

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.lucianghimpu.matchmefy.presentation.MainActivity
import com.lucianghimpu.matchmefy.presentation.dialogs.loading.LoadingDialogFragment


/**
 * Basically the same as [com.lucianghimpu.matchmefy.presentation.BaseFragment]
 * but simplified
 *
 * But as multiple inheritance is not supported in kotlin
 * this is the simple way to also provide mvvm support to [DialogFragment]s
 *
 * Also provides extra logic related to dialogs
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
        mainActivity = activity as MainActivity
        mainActivity.setBottomNavigationBarVisibility(View.GONE)
    }

    override fun onResume() {
        super.onResume()

        val isLoadingDialogFragment = this is LoadingDialogFragment

        val window: Window? = dialog!!.window
        val size = Point()

        val display: Display = window?.windowManager!!.defaultDisplay
        display.getSize(size)

        val width = size.x

        val sizeFactor = if (isLoadingDialogFragment) 0.27 else 0.83
        window.setLayout((width * sizeFactor).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setGravity(Gravity.TOP)

        if (!isLoadingDialogFragment) {
            val params = window.attributes
            // Based on the adobe xd values
            // To calculate for every screen size -> new Y = old Y * new Height / old Height
            // In adobe xd old Y is 124 but in real devices 1/4 of of the height looks better
            params.y = 160 * size.y / 640
            window.attributes = params
        }
    }
}