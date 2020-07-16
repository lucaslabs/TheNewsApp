package com.thenewsapp.presentation

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.thenewsapp.R

fun ImageView.loadUrl(urlToImage: String?) {
    Glide.with(context)
        .load(urlToImage)
        .placeholder(R.drawable.ic_image_placeholder)
        .into(this)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}