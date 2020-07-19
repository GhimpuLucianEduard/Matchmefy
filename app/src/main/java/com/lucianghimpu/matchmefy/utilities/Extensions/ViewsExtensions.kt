package com.lucianghimpu.matchmefy.utilities.Extensions

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.widget.doOnTextChanged
import androidx.viewpager2.widget.ViewPager2
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.utilities.createPlaylistDescText

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

fun TextView.withColoredSpan(startIndex: Int, endIndex: Int) {
    val spannable = SpannableStringBuilder(text)
    spannable.setSpan(
        ForegroundColorSpan(context.getColor(R.color.pastelRose)),
        startIndex,
        endIndex,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    this.text = spannable
}