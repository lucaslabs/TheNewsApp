package com.thenewsapp.presentation

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(urlToImage: String?) {
    urlToImage?.let { url ->
        Glide.with(context).load(url).into(this)
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}