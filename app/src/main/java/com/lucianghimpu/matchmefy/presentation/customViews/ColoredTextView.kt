package com.lucianghimpu.matchmefy.presentation.customViews

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doAfterTextChanged
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.utilities.Extensions.empty

class ColoredTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var startIndex: Int = -1
    private var endIndex: Int = -1

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ColoredTextView)
        startIndex = attributes.getInt(R.styleable.ColoredTextView_startIndex, -1)
        endIndex = attributes.getInt(R.styleable.ColoredTextView_endIndex, -1)
        updateColoredSpan(text.toString())
        doAfterTextChanged {
            updateColoredSpan(it.toString())
        }

        attributes.recycle()
    }

    private fun updateColoredSpan(newText: String) {
        if (text.isNullOrEmpty()) {
            this.text = String.empty
        } else {
            if (startIndex != -1 && endIndex != -1) {
                val spannable = SpannableStringBuilder(text)
                spannable.setSpan(
                    ForegroundColorSpan(context.getColor(R.color.pastelRose)),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )

                this.text = spannable
            }
        }
    }
}