package com.lucianghimpu.matchmefy.utilities

import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.Image
import com.lucianghimpu.matchmefy.utilities.Extensions.empty

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


// TODO: refactor spannable adapters into a single one
@BindingAdapter("matchScoreText")
fun TextView.matchScoreText(score: Number?) {

    if (score == null) {
        this.text = String.empty
        return
    }

    val finalString = context.getString(R.string.match_score_subtitle, score.toFloat())
    val spannable = SpannableStringBuilder(finalString)

    // + 1 for the "%" sign
    val lenOfColoredText = "%.2f".format(score.toFloat()).length + 1

    spannable.setSpan(
        ForegroundColorSpan(context.getColor(R.color.pastelRose)),
        11, // start
        11 + lenOfColoredText, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    this.text = spannable
}

@BindingAdapter("createPlaylistDescText")
fun TextView.createPlaylistDescText(user: String?) {

    if (user.isNullOrEmpty()) {
        this.text = String.empty
        return
    }

    val finalString = context.getString(R.string.create_playlist_description, user)
    val spannable = SpannableStringBuilder(finalString)

    val lenOfColoredText = user.length

    spannable.setSpan(
        ForegroundColorSpan(context.getColor(R.color.pastelRose)),
        82, // start
        82 + lenOfColoredText, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    this.text = spannable
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) VISIBLE else GONE
}