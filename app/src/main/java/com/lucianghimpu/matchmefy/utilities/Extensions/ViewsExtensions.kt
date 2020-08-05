package com.lucianghimpu.matchmefy.utilities.Extensions

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setShowSideItems(pageMarin: Int, offset: Int) {

    clipToPadding = false
    clipChildren = false
    offscreenPageLimit = 3

    setPageTransformer { page, position ->

        val offset = position * -(2 * offset + pageMarin)
        if (this.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
        } else {
            page.translationY = offset
        }
    }

}

fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.addScrollToTopListener(recyclerView: RecyclerView) {
    this.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            recyclerView.scrollToPosition(0)
        }
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            recyclerView.scrollToPosition(0)
        }
        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            recyclerView.scrollToPosition(0)
        }
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            recyclerView.scrollToPosition(0)
        }
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            recyclerView.scrollToPosition(0)
        }
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            recyclerView.scrollToPosition(0)
        }
    })
}