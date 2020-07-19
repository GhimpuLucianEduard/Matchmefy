package com.lucianghimpu.matchmefy.utilities

import android.app.Activity
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.lucianghimpu.matchmefy.R
import com.lucianghimpu.matchmefy.data.dataModels.Image
import com.lucianghimpu.matchmefy.utilities.Extensions.empty
import kotlinx.android.synthetic.main.layout_carousel_track_item.view.*

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

@BindingAdapter("imageFromId")
fun ImageView.bindImageId(imageId: Int) {
    if (imageId == 0) {
        return
    }
    this.background = ResourcesCompat.getDrawable(context.resources, imageId, null)
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) VISIBLE else GONE
}

// TODO: refactor spannable adapters into a single one OR move them to specific view files
// UPDATE 19.07 -> decided to move all spannable code in specific view, all of these should also be moved
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

@BindingAdapter(value = ["text", "span"], requireAll = false)
fun TextView.withColoredSpan(text: String?, span: ColoredTextSpan?) {

    if (text.isNullOrEmpty()) {
        this.text = String.empty
        return
    }

    if (span != null) {
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(
            ForegroundColorSpan(context.getColor(R.color.pastelRose)),
            span.startIndex,
            span.endIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        this.text = spannable
    }
}