package com.lucianghimpu.matchmefy.presentation.customViews

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doAfterTextChanged
import com.lucianghimpu.matchmefy.R

class ColoredTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var startIndex: Int = -1
    private var endIndex: Int = -1

    private var span: ForegroundColorSpan? = null

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ColoredTextView)
        startIndex = attributes.getInt(R.styleable.ColoredTextView_startIndex, -1)
        endIndex = attributes.getInt(R.styleable.ColoredTextView_endIndex, -1)
        val spannableString = SpannableString(text)
        setText(spannableString, BufferType.SPANNABLE)
        updateColoredSpan()
        doAfterTextChanged {
            updateColoredSpan()
        }
        attributes.recycle()
    }

    fun setStartIndex(newInt: Int) {
        startIndex = newInt
        updateColoredSpan()
    }

    fun setEndIndex(newInt: Int) {
        endIndex = newInt
        updateColoredSpan()
    }

    private fun updateColoredSpan() {
        val spannableText = text as Spannable
        if (spannableText.isNotEmpty() && startIndex != -1 && endIndex != -1) {
            spannableText.removeSpan(span)
            span = ForegroundColorSpan(context.getColor(R.color.pastelRose))
            spannableText.setSpan(
                span,
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
}