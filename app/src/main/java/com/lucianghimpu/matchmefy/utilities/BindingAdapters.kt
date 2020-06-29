package com.lucianghimpu.matchmefy.utilities

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.Image

@BindingAdapter("imageFromUriWithGlide")
fun ImageView.bindImageFromUriWithGlide(images: List<Image>?) {

    var imageUrl: String? = null

    if (images != null && images.isNotEmpty()) {
        imageUrl = images[0].url
    }

    Glide.with(context)
        .load(imageUrl)
        .fallback(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(this)
}

@BindingAdapter("imageFromUriWithGlide")
fun ImageView.bindImageFromUriWithGlide(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .fallback(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(this)
}

@set:BindingAdapter("visibleOrGone")
var View.visibleOrGone
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }