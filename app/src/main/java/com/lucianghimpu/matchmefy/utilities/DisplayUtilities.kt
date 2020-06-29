package com.lucianghimpu.matchmefy.utilities

import android.content.Context

class DensityUtil {

    companion object {
        fun dpToPixel(context: Context, dpValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun pixelToDp(context: Context, pixelValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (pixelValue / scale + 0.5f).toInt()
        }
    }
}