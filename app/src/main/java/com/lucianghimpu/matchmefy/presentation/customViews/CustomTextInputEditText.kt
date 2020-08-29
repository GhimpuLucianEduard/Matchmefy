package com.lucianghimpu.matchmefy.presentation.customViews

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.textfield.TextInputEditText
import com.lucianghimpu.matchmefy.R


class CustomTextInputEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputEditText(context, attrs, defStyleAttr) {

    private var clearTextDrawable: Drawable = context.getDrawable(R.drawable.clear)!!
    private var searchDrawable: Drawable = context.getDrawable(R.drawable.search_box_icon)!!

    init {

        // show search icon
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            searchDrawable, null, null, null)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                showClearButton()
                if (s.toString().isEmpty()) {
                    hideClearButton()
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        setOnTouchListener { _, event ->
            if (compoundDrawablesRelative[2] != null) {
                val clearButtonEnd = this.width - clearTextDrawable.intrinsicWidth

                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= clearButtonEnd) {
                        text!!.clear()
                        hideClearButton()
                    }
                }
                false
            }
            false
        }
    }

    private fun showClearButton() = setCompoundDrawablesRelativeWithIntrinsicBounds(
        searchDrawable, null, clearTextDrawable, null
    )

    private fun hideClearButton() = setCompoundDrawablesRelativeWithIntrinsicBounds(
        searchDrawable, null, null, null
    )
}