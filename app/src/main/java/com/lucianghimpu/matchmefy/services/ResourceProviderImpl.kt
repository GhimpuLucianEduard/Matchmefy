package com.lucianghimpu.matchmefy.services

import android.content.Context
import android.graphics.drawable.Drawable

class ResourceProviderImpl(
    val context: Context
) : ResourceProvider {
    override fun getString(resourceId: Int): String {
        return context.getString(resourceId)
    }

    override fun getString(resourceId: Int, vararg formatArgs: Any): String {
        return context.getString(resourceId, *formatArgs)
    }

    override fun getDrawable(resourceId: Int): Drawable? {
        return context.getDrawable(resourceId)
    }
}