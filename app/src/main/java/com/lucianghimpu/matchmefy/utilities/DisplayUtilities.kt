package com.lucianghimpu.matchmefy.utilities

import android.content.Context
import kotlin.math.sqrt

class DisplayUtil {

    companion object {
        fun dpToPixel(context: Context, dpValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun pixelToDp(context: Context, pixelValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (pixelValue / scale + 0.5f).toInt()
        }

        fun isTablet(context: Context): Boolean {
            val metrics = context.resources.displayMetrics

            val yInches = metrics.heightPixels / metrics.ydpi
            val xInches = metrics.widthPixels / metrics.xdpi
            val diagonalInches = sqrt(xInches * xInches + yInches * yInches.toDouble())
            var sizeFactor: Double
            return diagonalInches >= 7.0
        }
    }
}