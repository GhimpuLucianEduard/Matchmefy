package com.lucianghimpu.matchmefy.appServices

import android.graphics.drawable.Drawable

interface ResourceProvider {
    fun getString(resourceId: Int): String
    fun getString(resourceId: Int, vararg formatArgs: Any) : String
    fun getDrawable(resourceId: Int): Drawable?
}