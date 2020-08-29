package com.lucianghimpu.matchmefy.presentation.welcome

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.databinding.FragmentWelcomeBinding
import com.lucianghimpu.matchmefy.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*
import org.koin.android.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeViewModel, FragmentWelcomeBinding>() {

    override val viewModel: WelcomeViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_welcome
    override fun setViewDataBindingViewModel() { binding.viewModel = viewModel }

    private lateinit var pageViewAdapter: WelcomePageViewerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
    }

    private fun initViewPager() {
        pageViewAdapter = WelcomePageViewerAdapter(this, viewModel)
        viewPager.adapter = pageViewAdapter
        wormDotsIndicator.dotsClickable = false
        wormDotsIndicator.isClickable
        wormDotsIndicator.setViewPager2(viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                getStartedButton.visibility = if (position == WelcomeViewModel.ON_BOARDING_STATES_COUNT - 1)
                    View.VISIBLE else View.GONE
            }
        })
    }
}
