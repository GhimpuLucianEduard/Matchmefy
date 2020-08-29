package com.lucianghimpu.matchmefy.presentation.welcome

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WelcomePageViewerAdapter(
    fragment: WelcomeFragment,
    private val viewModel: WelcomeViewModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> WelcomeProfileFragment()
            else -> OnBoardingFragment(viewModel.getStateForIndex(position))
        }
    }
}